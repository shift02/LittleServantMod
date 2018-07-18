package littleservantmod.profession;

import java.util.Map;

import com.google.common.collect.Maps;

import littleservantmod.api.IServant;
import littleservantmod.api.LittleServantModAPI;
import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.entity.ai.EntityAIFollow;
import littleservantmod.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.ResourceLocation;

/** 無職 */
public class ProfessionUnemployed extends ProfessionLSMBase {

	public IMode modeBasic;

	public IBehavior behaviorBasic;

	public ProfessionUnemployed() {

		//Mode
		this.modeBasic = LittleServantModAPI.professionManager.getBasicMode();

		//Behavior
		this.behaviorBasic = LittleServantModAPI.professionManager.getBasicBehavior();

	}

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//うさぎについていく
		servant.addAI(500, new EntityAIFollow(servant.getEntityInstance(), EntityRabbit.class, 0.5D));

		//ウロウロ
		servant.addAI(800, new EntityAIWanderAvoidWater(servant.getEntityInstance(), 0.5D));

		//うさぎを見る
		servant.addAI(900, new EntityAIWatchClosest(servant.getEntityInstance(), EntityRabbit.class, 4.0F));

	}

	@Override
	public boolean isEnableProfession(IServant servant) {
		return servant.getOwner() == null;
	}

	@Override
	public Map<ResourceLocation, IMode> initModes(IServant servant) {

		Map<ResourceLocation, IMode> map = Maps.newLinkedHashMap();

		map.put(LittleServantModAPI.professionManager.getBasicModeKey(), this.modeBasic);

		return map;

	}

	@Override
	public Map<ResourceLocation, IBehavior> initBehavior(IServant servant) {

		Map<ResourceLocation, IBehavior> map = Maps.newLinkedHashMap();

		map.put(LittleServantModAPI.professionManager.getBasicBehaviorKey(), behaviorBasic);

		return map;

	}

	@Override
	public IMode getDefaultMode(IServant servant) {
		return this.modeBasic;
	}

	@Override
	public IBehavior getDefaultBehavior(IServant servant) {
		return this.behaviorBasic;
	}

}
