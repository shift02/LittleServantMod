package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIEquipTool;
import littleservantmod.entity.ai.EntityAIUseTorch;
import littleservantmod.entity.ai.EntityAIUseTorchMoveToBlock;

public class ProfessionTorcher extends ProfessionLSMBase {

    @Override
    public void initAI(IServant servant) {

        super.initAI(servant);

        //松明に持ち替える
        servant.addAI(200, new EntityAIEquipTool((EntityLittleServant) servant.getEntityInstance(), ServantToolManager.torcher));

        //500
        servant.addAI(500, new EntityAIUseTorchMoveToBlock((EntityLittleServant) servant.getEntityInstance(), 0.8d));

        //足元に松明
        servant.addAI(600, new EntityAIUseTorch((EntityLittleServant) servant.getEntityInstance()));

        //Target
        /*
        servant.addBlockTargetAI(200, new EntityAITargetBlock((EntityLittleServant) servant.getEntityInstance(), 20, (world, pos) -> {
            System.out.println("ProfessionTorcher " + world.getLight(pos));
            return world.getLight(pos) <= 8;
        }));
        */

    }
}
