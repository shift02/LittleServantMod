package littleservantmod.api.profession;

import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import net.minecraft.util.ResourceLocation;

public interface IProfessionManager {

	@Deprecated
	public void registerProfession(IProfessionFactory factory);

	public ResourceLocation getBasicModeKey();

	public IMode getBasicMode();

	public ResourceLocation getBasicBehaviorKey();

	public IBehavior getBasicBehavior();

}
