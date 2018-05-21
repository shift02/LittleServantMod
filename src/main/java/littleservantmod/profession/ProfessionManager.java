package littleservantmod.profession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.AttachProfessionEvent;
import littleservantmod.api.profession.IProfessionFactory;
import littleservantmod.api.profession.IProfessionManager;
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

	public Map<ResourceLocation, ProfessionBase> professions = new HashMap<>();

	public List<IProfessionFactory> professionFactorys = new ArrayList<>();

	public ProfessionDispatcher gatProfessions(IServant servant) {

		AttachProfessionEvent event = new AttachProfessionEvent(servant);
		MinecraftForge.EVENT_BUS.post(event);

		return new ProfessionDispatcher(event.getProfessions());
	}

	public void registProfession(ProfessionBase profession) {

		professions.put(profession.getID(), profession);

	}

	public ProfessionBase getProfession(ResourceLocation resourceLocation) {

		if (!this.professions.containsKey(resourceLocation)) {
			this.professions.get(ProfessionUnemployed.resourceLocation);
		}

		return this.professions.get(resourceLocation);

	}

	@Override
	public void registerProfession(IProfessionFactory factory) {

		professionFactorys.add(factory);

	}

}
