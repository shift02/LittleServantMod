package littleservantmod.api;

import littleservantmod.api.profession.AttachProfessionEvent;
import littleservantmod.api.profession.IProfessionFactory;
import littleservantmod.api.profession.IProfessionManager;
import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.api.profession.mode.ModeNone;
import net.minecraft.util.ResourceLocation;

public class LittleServantModAPI {

	public static final String MOD_ID = "littleservantmod";

	public static IProfessionManager professionManager;

	/**
	 * 職業を登録する
	 * @param factory
	 * @deprecated {@link AttachProfessionEvent}を使用する
	 */
	@Deprecated
	public static void registerProfession(IProfessionFactory factory) {

		professionManager.registerProfession(factory);

	}

	public static IMode getBasicMode() {
		return professionManager.getBasicMode();
	}

	public static IBehavior getBasicBehavior() {
		return professionManager.getBasicBehavior();
	}

	public static IMode noneMode = new ModeNone().setUnlocalizedName("mode_none").setRegistryName(new ResourceLocation(MOD_ID, "mode_none"));

}
