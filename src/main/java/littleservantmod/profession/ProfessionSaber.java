package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIEquipShield;

public class ProfessionSaber extends ProfessionLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//シールドを持っているときは持ち変える
		servant.addAI(200, new EntityAIEquipShield((EntityLittleServant) servant.getEntityInstance()));

	}

	@Override
	public boolean hasOption(IServant servant) {
		return true;
	}

}
