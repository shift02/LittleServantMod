package littleservantmod.api;

import littleservantmod.api.profession.AttachProfessionEvent;
import littleservantmod.api.profession.IProfessionFactory;
import littleservantmod.api.profession.IProfessionManager;

public class LittleServantModAPI {

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

}
