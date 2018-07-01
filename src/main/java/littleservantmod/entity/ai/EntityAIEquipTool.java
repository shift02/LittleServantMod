package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.profession.ProfessionToolManager;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class EntityAIEquipTool extends EntityAIBase {

	protected EntityLittleServant servant;

	protected ResourceLocation type;

	public EntityAIEquipTool(EntityLittleServant servant, ResourceLocation type) {

		this.servant = servant;
		this.type = type;

	}

	@Override
	public boolean shouldExecute() {

		if (this.isTool(this.servant.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND))) return false;

		return true;
	}

	public boolean isTool(ItemStack item) {

		if (item.isEmpty()) return false;

		if (ProfessionToolManager.getInstance().isTool(type, item)) return true;

		return false;

	}

	@Override
	public void updateTask() {

		NonNullList<ItemStack> mainInventory = this.servant.inventory.mainInventory;

		ItemStack mainH = this.servant.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

		for (int i = 0; i < mainInventory.size(); i++) {
			if (isTool(mainInventory.get(i))) {
				this.servant.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.servant.inventory.getStackInSlot(i));
				this.servant.inventory.setInventorySlotContents(i, mainH);
				return;
			}
		}

	}

}