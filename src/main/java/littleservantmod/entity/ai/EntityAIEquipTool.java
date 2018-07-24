package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.profession.ProfessionToolManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class EntityAIEquipTool extends EntityAIEquip {

	public EntityAIEquipTool(EntityLittleServant servant, ResourceLocation type) {
		super(servant, EntityEquipmentSlot.MAINHAND, stack -> ProfessionToolManager.getInstance().isTool(type, stack));
	}

}