package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

/**
 * 盾を装備するAI
 * @author shift02
 *
 */
public class EntityAIEquipShield extends EntityAIEquip {
	private static final ItemStack SHIELD = new ItemStack(Items.SHIELD);
	private static final Predicate<ItemStack> IS_SHIELD = SHIELD::isItemEqualIgnoreDurability;

	public EntityAIEquipShield(EntityLittleServant servant) {
		super(servant, EntityEquipmentSlot.OFFHAND, IS_SHIELD);
	}

}
