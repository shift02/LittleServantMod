package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * 縦を装備するAI
 * @author shift02
 *
 */
public class EntityAIEquipShield extends EntityAIBase {

	protected EntityLittleServant servant;

	public EntityAIEquipShield(EntityLittleServant servant) {

		this.servant = servant;

	}

	@Override
	public boolean shouldExecute() {

		if (this.isShield(this.servant.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND))) return false;

		return true;
	}

	public boolean isShield(ItemStack item) {

		if (item.isEmpty()) return false;

		if (item.isItemEqualIgnoreDurability(new ItemStack(Items.SHIELD))) return true;

		return false;

	}

	@Override
	public void updateTask() {

		int shieldSlot = this.servant.inventory.getSlotFor(new ItemStack(Items.SHIELD));
		if (shieldSlot == -1) return;

		ItemStack off = this.servant.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

		this.servant.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, this.servant.inventory.getStackInSlot(shieldSlot));

		this.servant.inventory.setInventorySlotContents(shieldSlot, off);

	}

}
