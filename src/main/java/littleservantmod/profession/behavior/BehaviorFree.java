package littleservantmod.profession.behavior;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServantFakePlayer;
import littleservantmod.entity.ai.EntityAIMoveTowardsRestriction2;
import littleservantmod.entity.ai.EntityAIWanderAvoidWater;

public class BehaviorFree extends BehaviorLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//範囲内をキープ
		servant.addAI(300, new EntityAIMoveTowardsRestriction2((EntityLittleServantFakePlayer) servant.getEntityInstance(), 0.8D));

		//ウロウロ
		servant.addAI(800, new EntityAIWanderAvoidWater(servant.getEntityInstance(), 0.5D));

	}

	@Override
	public void startBehavior(IServant servant) {

		EntityLittleServantFakePlayer en = (EntityLittleServantFakePlayer) servant.getEntityInstance();

		en.setHomePosAndDistance(en.getPosition(), 10);

	}

}
