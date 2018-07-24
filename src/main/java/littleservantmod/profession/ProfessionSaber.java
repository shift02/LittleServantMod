package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIAttackMelee2;
import littleservantmod.entity.ai.EntityAIEquip;
import littleservantmod.entity.ai.EntityAIEquipTool;
import littleservantmod.entity.ai.target.EntityAINearestAttackableTarget2;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class ProfessionSaber extends ProfessionLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//シールドかトーテムを持っているときは持ち替える
		servant.addAI(200, new EntityAIEquip(servant.getEntityInstance(), EntityEquipmentSlot.OFFHAND, ((Predicate<ItemStack>) new ItemStack(Items.TOTEM_OF_UNDYING)::isItemEqual).or(new ItemStack(Items.SHIELD)::isItemEqualIgnoreDurability)));

		//剣に持ち替える
		servant.addAI(200, new EntityAIEquipTool((EntityLittleServant) servant.getEntityInstance(), ProfessionToolManager.saber));

		//攻撃
		servant.addAI(500, new EntityAIAttackMelee2((EntityLittleServant) servant.getEntityInstance(), 0.8d, false));

		//Target
		servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntitySpider.class, true));

	}

	@Override
	public boolean hasOption(IServant servant) {
		return true;
	}

}
