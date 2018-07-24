package littleservantmod.inventory;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * GUIのサーバー側の処理
 * @author shift02
 */
public class ContainerServant extends ContainerServantBase {

	private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] { EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };

	public ContainerServant(InventoryPlayer playerInventory, IInventory servantInventoryIn, EntityLittleServant servantIn) {
		super(playerInventory, servantInventoryIn, servantIn);

		//Main size
		int slotIndex = 16;

		//装備 (Servantは小さいので帽子が不要)
		for (int k = 0; k < 3; ++k) {

			final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
			this.addSlotToContainer(new Slot(servantInventoryIn, slotIndex + (3 - k), 8, 8 + 0 + k * 18) {
				/**
				 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
				 * in the case of armor slots)
				 */
				@Override
				public int getSlotStackLimit() {
					return 1;
				}

				/**
				 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace
				 * fuel.
				 */
				@Override
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem().isValidArmor(stack, entityequipmentslot, servant);
				}

				/**
				 * Return whether this slot's stack can be taken from this slot.
				 */
				@Override
				public boolean canTakeStack(EntityPlayer playerIn) {
					ItemStack itemstack = this.getStack();
					return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(playerIn);
				}

				@Override
				@Nullable
				@SideOnly(Side.CLIENT)
				public String getSlotTexture() {
					return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
				}
			});
		}

		//サーヴァントのアイテム欄
		for (int l = 0; l < 2; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				if (l != 1 || j1 < 8) this.addSlotToContainer(new Slot(servantInventoryIn, j1 + l * 9, 8 + j1 * 18, 84 - 8 + l * 18));
			}
		}
		//頭の部分
		this.addSlotToContainer(new Slot(servantInventoryIn, 20, 8 + 8 * 18, 84 - 8 + 1 * 18));

		//プレイヤーのアイテム欄
		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlotToContainer(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + 42 + l * 18));
			}
		}

		//プレイヤーのアイテム欄その2
		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 142 + 42));
		}

	}

	@Override
	@Nonnull
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);

		if (slot == null || !slot.getHasStack()) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getStack();
		int servantInvSize = 21;
		int playerInvSize = 36;

		// Servant -> Player
		if (index < servantInvSize && !mergeItemStack(stack, servantInvSize, servantInvSize + playerInvSize, true))
			return ItemStack.EMPTY;
		// Player -> Servant
		if (index >= servantInvSize && index < servantInvSize + playerInvSize && !mergeItemStack(stack, 0, servantInvSize, false))
			return ItemStack.EMPTY;
		// Others
		if (!mergeItemStack(stack, 0, servantInvSize + playerInvSize, false))
			return ItemStack.EMPTY;

		if (stack.isEmpty()) {
			slot.putStack(ItemStack.EMPTY);
		} else {
			slot.onSlotChanged();
		}

		return stack.copy();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
