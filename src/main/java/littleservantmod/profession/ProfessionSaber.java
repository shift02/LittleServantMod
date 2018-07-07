package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIAttackMelee2;
import littleservantmod.entity.ai.EntityAIEquipShield;
import littleservantmod.entity.ai.EntityAIEquipTool;
import littleservantmod.entity.ai.target.EntityAINearestAttackableTarget2;
import net.minecraft.entity.monster.EntitySpider;

public class ProfessionSaber extends ProfessionLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//シールドを持っているときは持ち変える
		servant.addAI(200, new EntityAIEquipShield((EntityLittleServant) servant.getEntityInstance()));

		//剣を持ち帰る
		servant.addAI(200, new EntityAIEquipTool((EntityLittleServant) servant.getEntityInstance(), ProfessionToolManager.saber));

		//攻撃
		servant.addAI(400, new EntityAIAttackMelee2((EntityLittleServant) servant.getEntityInstance(), 1.2d, false));

		//Target
		servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntitySpider.class, true));

	}

	@Override
	public boolean hasOption(IServant servant) {
		return true;
	}

}
