package littleservantmod.entity;

import littleservantmod.LSMProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityLittleServant extends EntityLittleServantProfession {

	public boolean isGui = false;

	public EntityLittleServant() {
		super(null);
	}

	public EntityLittleServant(World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		boolean flag = !itemstack.isEmpty();

		if (flag && itemstack.getItem() == Items.SPAWN_EGG) {
			return super.processInteract(player, hand);
		}

		if (flag && itemstack.getItem() == Items.CAKE) {
			return this.processInteractCake(player, hand, itemstack);
		}

		if (this.isTamed()) {
			return this.processInteractTamed(player, hand, itemstack);
		}

		return true;

	}

	public boolean processInteractCake(EntityPlayer player, EnumHand hand, ItemStack itemstack) {

		if (this.isTamed()) {
			return false;
		}

		if (!player.capabilities.isCreativeMode) {
			itemstack.shrink(1);
		}

		if (!this.world.isRemote) {

			if (this.rand.nextInt(3) == 0 || true) {

				//テイム成功の処理
				this.setTamedBy(player);
				this.navigator.clearPath();
				this.setAttackTarget((EntityLivingBase) null);
				this.aiSit.setSitting(true);
				this.setHealth(20.0F);
				this.playTameEffect(true);

				//職業を雑用に変更
				this.changeProfession(this.professions.getDefaultTamedProfession());

				this.world.setEntityState(this, (byte) 7);

			} else {
				this.playTameEffect(false);
				this.world.setEntityState(this, (byte) 6);
			}

		}

		return true;

	}

	public boolean processInteractTamed(EntityPlayer player, EnumHand hand, ItemStack itemstack) {

		if (this.isOwner(player) && itemstack.getItem().equals(Items.SUGAR)) {

			if (!this.world.isRemote) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPath();
				this.setAttackTarget((EntityLivingBase) null);

			} else {
				this.playSitEffect(!this.isSitting());
			}

			return true;

		}

		if (!this.world.isRemote) LSMProxy.getProxy().openGui(player, this);

		return true;

	}

	protected void playSitEffect(boolean play) {
		EnumParticleTypes enumparticletypes = EnumParticleTypes.NOTE;

		if (!play) enumparticletypes = EnumParticleTypes.SPELL_WITCH;

		for (int i = 0; i < 4; ++i) {
			double d0 = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.world.spawnParticle(enumparticletypes, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.9D + this.rand.nextFloat() * this.height,
					this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
		}
	}

}
