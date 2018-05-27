package littleservantmod.profession;

import littleservantmod.LittleServantMod;
import littleservantmod.api.profession.AttachProfessionEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * LSMで使用するデフォルトの職業を定義する
 * */
public class ProfessionEventHandler {

	/** 無職 */
	public static ResourceLocation kyeUnemployed = new ResourceLocation(LittleServantMod.MOD_ID, "unemployed");

	/** 雑用 */
	public static ResourceLocation keyChores = new ResourceLocation(LittleServantMod.MOD_ID, "chores");

	@SubscribeEvent
	public void onAttachProfessionEvent(AttachProfessionEvent evt) {

		//無職
		evt.addProfession(kyeUnemployed, new ProfessionUnemployed().setRegistryName(kyeUnemployed));

		//雑用
		evt.addProfession(keyChores, new ProfessionChores().setRegistryName(keyChores));

	}

}
