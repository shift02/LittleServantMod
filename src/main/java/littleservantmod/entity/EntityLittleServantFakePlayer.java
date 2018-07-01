package littleservantmod.entity;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import littleservantmod.ForgeLSMHooks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

/**
 * サーヴァントにプレイヤーと同じ動作をできるようにするクラス <br />
 * プレイヤー由来の処理もここに
 * @author shift02
 *
 */
public abstract class EntityLittleServantFakePlayer extends EntityLittleServantBase {

	protected EntityPlayer player;

	/** Inventory of the player */
	public InventoryServant inventory;

	protected static final DataParameter<Integer> CURRENT_ITEN = EntityDataManager.<Integer> createKey(EntityLittleServantBase.class, DataSerializers.VARINT);

	public EntityLittleServantFakePlayer(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {

		super.entityInit();

		if (!world.isRemote) this.player = FakePlayerFactory.getMinecraft((WorldServer) world);
		inventory = new InventoryServant((EntityLittleServant) this, player);
		this.dataManager.register(CURRENT_ITEN, 0);

	}

	/* ======================
	 *  インベントリ関係
	 * ====================== */

	/**
	 * 手に持っているアイテム
	 * */
	@Override
	public Iterable<ItemStack> getHeldEquipment() {
		return Lists.newArrayList(this.getHeldItemMainhand(), this.getHeldItemOffhand());
	}

	/**
	 * アーマー
	 * */
	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return this.inventory.armorInventory;
	}

	/**
	 * 装備取得処理のブリッジ
	 * */
	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		if (slotIn == EntityEquipmentSlot.MAINHAND) {
			return this.inventory.getCurrentItem();
		} else if (slotIn == EntityEquipmentSlot.OFFHAND) {
			return this.inventory.offHandInventory.get(0);
		} else {
			return slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR ? (ItemStack) this.inventory.armorInventory.get(slotIn.getIndex()) : ItemStack.EMPTY;
		}
	}

	/**
	 * 装備設定処理のブリッジ
	 */
	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		if (slotIn == EntityEquipmentSlot.MAINHAND) {
			this.playEquipSound(stack);
			this.inventory.mainInventory.set(this.inventory.currentItem, stack);
		} else if (slotIn == EntityEquipmentSlot.OFFHAND) {
			this.playEquipSound(stack);
			this.inventory.offHandInventory.set(0, stack);
		} else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
			this.playEquipSound(stack);
			this.inventory.armorInventory.set(slotIn.getIndex(), stack);
		}
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	/**
	 * Drops an item into the world.
	 */
	@Nullable
	public EntityItem dropItem(ItemStack itemStackIn, boolean unused) {
		return ForgeLSMHooks.onPlayerTossEvent((EntityLittleServant) this, itemStackIn, false);
	}

	@Nullable
	public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem) {
		if (droppedItem.isEmpty()) {
			return null;
		} else {
			double d0 = this.posY - 0.30000001192092896D + this.getEyeHeight();
			EntityItem entityitem = new EntityItem(this.world, this.posX, d0, this.posZ, droppedItem);
			entityitem.setPickupDelay(40);

			if (traceItem) {
				entityitem.setThrower(this.getName());
			}

			if (dropAround) {
				float f = this.rand.nextFloat() * 0.5F;
				float f1 = this.rand.nextFloat() * ((float) Math.PI * 2F);
				entityitem.motionX = -MathHelper.sin(f1) * f;
				entityitem.motionZ = MathHelper.cos(f1) * f;
				entityitem.motionY = 0.20000000298023224D;
			} else {
				float f2 = 0.3F;
				entityitem.motionX = -MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
				entityitem.motionZ = MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
				entityitem.motionY = -MathHelper.sin(this.rotationPitch * 0.017453292F) * f2 + 0.1F;
				float f3 = this.rand.nextFloat() * ((float) Math.PI * 2F);
				f2 = 0.02F * this.rand.nextFloat();
				entityitem.motionX += Math.cos(f3) * f2;
				entityitem.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
				entityitem.motionZ += Math.sin(f3) * f2;
			}

			ItemStack itemstack = this.dropItemAndGetStack(entityitem);

			if (traceItem) {
				if (!itemstack.isEmpty()) {
					//this.addStat(StatList.getDroppedObjectStats(itemstack.getItem()), droppedItem.getCount());
				}

				//this.addStat(StatList.DROP);
			}

			return entityitem;
		}
	}

	public ItemStack dropItemAndGetStack(EntityItem p_184816_1_) {
		if (captureDrops) capturedDrops.add(p_184816_1_);
		else // Forge: Don't indent to keep patch smaller.
			this.world.spawnEntity(p_184816_1_);
		return p_184816_1_.getItem();
	}

	public int getCurrentItem() {
		return this.dataManager.get(CURRENT_ITEN);
	}

	public void setCurrentItem(int currentItem) {
		this.inventory.currentItem = currentItem;
		this.dataManager.set(CURRENT_ITEN, currentItem);
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);

		if (CURRENT_ITEN.equals(key) && this.world.isRemote) {

			this.inventory.currentItem = getCurrentItem();

		}

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
		this.inventory.readFromNBT(nbttaglist);
		this.inventory.currentItem = compound.getInteger("SelectedItemSlot");

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
		//		compound.setInteger("SelectedItemSlot", this.inventory.currentItem);
		compound.setInteger("SelectedItemSlot", this.getCurrentItem());

	}

}
