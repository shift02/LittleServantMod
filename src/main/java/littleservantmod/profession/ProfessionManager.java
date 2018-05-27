package littleservantmod.profession;

import java.util.ArrayList;
import java.util.List;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.AttachProfessionEvent;
import littleservantmod.api.profession.IProfessionFactory;
import littleservantmod.api.profession.IProfessionManager;
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

	public static ProfessionDispatcher gatProfessions(IServant servant) {

		AttachProfessionEvent event = new AttachProfessionEvent(servant);
		MinecraftForge.EVENT_BUS.post(event);

		return new ProfessionDispatcher(event.getProfessions());

	}

	@Override
	public void registerProfession(IProfessionFactory factory) {

		professionFactorys.add(factory);

	}

}
