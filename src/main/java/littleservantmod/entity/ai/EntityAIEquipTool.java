package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.profession.ServantToolManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class EntityAIEquipTool extends EntityAIEquip {

	public EntityAIEquipTool(EntityLittleServant servant, ResourceLocation type) {
		super(servant, EntityEquipmentSlot.MAINHAND, stack -> ServantToolManager.getInstance().isTool(type, stack));
	}

}