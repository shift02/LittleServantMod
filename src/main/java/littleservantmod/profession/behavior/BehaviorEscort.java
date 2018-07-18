package littleservantmod.profession.behavior;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServantBase;
import littleservantmod.entity.EntityLittleServantFakePlayer;
import littleservantmod.entity.ai.EntityAIFollowOwner2;

public class BehaviorEscort extends BehaviorLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//基本的には職業を優先するけど離れ過ぎたら戻ってくる
		servant.addAI(300, new EntityAIFollowOwner2((EntityLittleServantBase) servant, 0.6D, 20.0F, 4.0F));

		//マスターについていく
		servant.addAI(600, new EntityAIFollowOwner2((EntityLittleServantBase) servant, 0.6D, 8.0F, 2.0F));

	}

	@Override
	public void startBehavior(IServant servant) {

		EntityLittleServantFakePlayer en = (EntityLittleServantFakePlayer) servant.getEntityInstance();

		en.clearHomePosition();

	}

}
