package littleservantmod.profession.mode;

import littleservantmod.LittleServantMod;
import littleservantmod.api.profession.mode.AttachProfessionModeEvent;
import littleservantmod.profession.IconHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModeEventHandler {

	/** 選択なし */
	public static ResourceLocation kyeDefault = new ResourceLocation(LittleServantMod.MOD_ID, "default");
	public static IconHolder iconDefault = new IconHolder();

	@SubscribeEvent
	public void onAttachProfessionEvent(AttachProfessionModeEvent evt) {

		//無職
		//evt.addProfession(kyeUnemployed,
		//		new ProfessionUnemployed().setIconHolder(iconUnemployed).setUnlocalizedName("unemployed").setRegistryName(kyeUnemployed));

	}

}
