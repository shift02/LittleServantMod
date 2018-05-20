package littleservantmod.profession;

import littleservantmod.LittleServantMod;
import net.minecraft.util.ResourceLocation;

public class ProfessionChores extends ProfessionBase {

	public static ResourceLocation resourceLocation = new ResourceLocation(LittleServantMod.MOD_ID, "chores");

	@Override
	public ResourceLocation getID() {
		return resourceLocation;
	}

}
