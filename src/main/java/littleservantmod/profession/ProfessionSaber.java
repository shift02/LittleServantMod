package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIEquipShield;
import littleservantmod.entity.ai.EntityAIEquipTool;

public class ProfessionSaber extends ProfessionLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//シールドを持っているときは持ち変える
		servant.addAI(200, new EntityAIEquipShield((EntityLittleServant) servant.getEntityInstance()));

		//剣を持ち帰る
		servant.addAI(200, new EntityAIEquipTool((EntityLittleServant) servant.getEntityInstance(), ProfessionToolManager.saber));

	}

	@Override
	public boolean hasOption(IServant servant) {
		return true;
	}

}
