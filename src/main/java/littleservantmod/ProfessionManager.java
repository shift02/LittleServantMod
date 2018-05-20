package littleservantmod;

import java.util.HashMap;
import java.util.Map;

import littleservantmod.profession.ProfessionBase;
import littleservantmod.profession.ProfessionUnemployed;
import net.minecraft.util.ResourceLocation;

public class ProfessionManager {

	private static ProfessionManager instance;

	public static ProfessionManager getInstance() {

		if (instance == null) {
			instance = new ProfessionManager();
		}

		return instance;

	}

	public Map<ResourceLocation, ProfessionBase> professions = new HashMap<>();

	public void registProfession(ProfessionBase profession) {

		professions.put(profession.getID(), profession);

	}

	public ProfessionBase getProfession(ResourceLocation resourceLocation) {

		if (!this.professions.containsKey(resourceLocation)) {
			this.professions.get(ProfessionUnemployed.resourceLocation);
		}

		return this.professions.get(resourceLocation);

	}

}
