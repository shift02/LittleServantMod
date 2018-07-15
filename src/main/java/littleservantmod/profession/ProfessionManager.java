package littleservantmod.profession;

import java.util.ArrayList;
import java.util.List;

import littleservantmod.LittleServantMod;
import littleservantmod.api.IServant;
import littleservantmod.api.profession.AttachProfessionEvent;
import littleservantmod.api.profession.IProfessionFactory;
import littleservantmod.api.profession.IProfessionManager;
import littleservantmod.api.profession.behavior.BehaviorBasic;
import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.api.profession.mode.ModeBasic;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class ProfessionManager implements IProfessionManager {

	private static ProfessionManager instance;

	public static ProfessionManager getInstance() {

		if (instance == null) {
			instance = new ProfessionManager();
		}

		return instance;

	}

	public List<IProfessionFactory> professionFactorys = new ArrayList<>();

	public ResourceLocation basicModeKey = new ResourceLocation(LittleServantMod.MOD_ID, "mode_basic");

	public ResourceLocation basicBehaviorKey = new ResourceLocation(LittleServantMod.MOD_ID, "behavior_basic");

	private ProfessionManager() {

	}

	public static ProfessionDispatcher gatProfessions(IServant servant) {

		AttachProfessionEvent event = new AttachProfessionEvent(servant);
		MinecraftForge.EVENT_BUS.post(event);

		return new ProfessionDispatcher(servant, event.getProfessions());

	}

	@Override
	public void registerProfession(IProfessionFactory factory) {

		professionFactorys.add(factory);

	}

	@Override
	public ResourceLocation getBasicModeKey() {
		return basicModeKey;
	}

	@Override
	public IMode getBasicMode() {
		return new ModeBasic().setUnlocalizedName("mode_basic").setRegistryName(basicModeKey);
	}

	@Override
	public ResourceLocation getBasicBehaviorKey() {
		return basicBehaviorKey;
	}

	@Override
	public IBehavior getBasicBehavior() {
		return new BehaviorBasic().setUnlocalizedName("behavior_basic").setRegistryName(basicBehaviorKey);
	}

}
