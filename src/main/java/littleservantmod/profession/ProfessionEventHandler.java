package littleservantmod.profession;

import littleservantmod.LittleServantMod;
import littleservantmod.api.profession.AttachProfessionEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * LSMで使用するデフォルトの職業を定義する
 * */
public class ProfessionEventHandler {

	/** 無職 */
	public static ResourceLocation kyeUnemployed = new ResourceLocation(LittleServantMod.MOD_ID, "unemployed");
	public static IconHolder iconUnemployed = new IconHolder();

	/** 雑用 */
	public static ResourceLocation keyChores = new ResourceLocation(LittleServantMod.MOD_ID, "chores");
	public static IconHolder iconChores = new IconHolder();

	/** 剣士 */
	public static ResourceLocation keySaber = new ResourceLocation(LittleServantMod.MOD_ID, "saber");
	public static IconHolder iconSaber = new IconHolder() {
		@Override
		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getIcon() {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:items/diamond_sword");
		}

	};

	@SubscribeEvent
	public void onAttachProfessionEvent(AttachProfessionEvent evt) {

		//無職
		evt.addProfession(kyeUnemployed,
				new ProfessionUnemployed().setIconHolder(iconUnemployed).setUnlocalizedName("unemployed").setRegistryName(kyeUnemployed));

		//雑用
		evt.addProfession(keyChores,
				new ProfessionChores().setIconHolder(iconSaber).setUnlocalizedName("chores").setRegistryName(keyChores));

		//剣士
		evt.addProfession(keySaber,
				new ProfessionSaber().setIconHolder(iconSaber).setUnlocalizedName("saber").setRegistryName(keySaber));

	}

}
