package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServantFakePlayer;
import littleservantmod.entity.ai.EntityAICollectEntityItem;

/**
 * 雑用
 * @author shift02
 */
public class ProfessionChores extends ProfessionLSMBase {

    public ProfessionChores() {

    }

    @Override
    public void initAI(IServant servant) {

        super.initAI(servant);

        //落ちているアイテムを拾う
        servant.addAI(500, new EntityAICollectEntityItem((EntityLittleServantFakePlayer) servant.getEntityInstance(), defaultSpeed, 4));

    }

}