package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIEquipTool;

public class ProfessionTorcher extends ProfessionLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//松明に持ち替える
		servant.addAI(200, new EntityAIEquipTool((EntityLittleServant) servant.getEntityInstance(), ProfessionToolManager.torcher));

		//500

	}
}
