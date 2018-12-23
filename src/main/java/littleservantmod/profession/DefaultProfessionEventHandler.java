package littleservantmod.profession;

import littleservantmod.LittleServantMod;
import littleservantmod.api.profession.AttachProfessionEvent;
import littleservantmod.client.util.IIconHolder;
import littleservantmod.client.util.IconHolder;
import littleservantmod.client.util.IconVanillaHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * LSMで使用するデフォルトの職業を定義する
 * */
public class DefaultProfessionEventHandler {

    /** 無職 */
    public static ResourceLocation kyeUnemployed = new ResourceLocation(LittleServantMod.MOD_ID, "unemployed");
    public static IconHolder iconUnemployed = new IconHolder();

    /** 雑用 */
    public static ResourceLocation keyChores = new ResourceLocation(LittleServantMod.MOD_ID, "chores");
    public static IconHolder iconChores = new IconHolder();

    /** 剣士 */
    public static ResourceLocation keySaber = new ResourceLocation(LittleServantMod.MOD_ID, "saber");
    public static IIconHolder iconSaber = new IconVanillaHolder(new ResourceLocation("items/diamond_sword"));

    /** 弓兵 */
    public static ResourceLocation keyArcher = new ResourceLocation(LittleServantMod.MOD_ID, "archer");
    public static IIconHolder iconArcher = new IconVanillaHolder(new ResourceLocation("items/bow_standby"));

    /** 毛刈り兵 */
    public static ResourceLocation keyRipper = new ResourceLocation(LittleServantMod.MOD_ID, "ripper");
    public static IIconHolder iconRipper = new IconVanillaHolder(new ResourceLocation("items/shears"));

    /** たいまつ兵 */
    public static ResourceLocation keyTorcher = new ResourceLocation(LittleServantMod.MOD_ID, "torcher");
    public static IIconHolder iconTorcher = new IconVanillaHolder(new ResourceLocation("blocks/torch_on"));

    @SubscribeEvent
    public void onAttachProfessionEvent(AttachProfessionEvent evt) {

        //無職
        evt.addProfession(kyeUnemployed, new ProfessionUnemployed().setIconHolder(iconUnemployed).setUnlocalizedName("unemployed").setRegistryName(kyeUnemployed));

        //雑用
        evt.addProfession(keyChores, new ProfessionChores().setIconHolder(iconChores).setUnlocalizedName("chores").setRegistryName(keyChores));

        //剣士
        evt.addProfession(keySaber, new ProfessionSaber().setIconHolder(iconSaber).setUnlocalizedName("saber").setRegistryName(keySaber));

        //弓兵
        evt.addProfession(keyArcher, new ProfessionArcher().setIconHolder(iconArcher).setUnlocalizedName("archer").setRegistryName(keyArcher));

        //毛刈り兵
        evt.addProfession(keyRipper, new ProfessionRipper().setIconHolder(iconRipper).setUnlocalizedName("ripper").setRegistryName(keyRipper));

        //たいまつ兵
        evt.addProfession(keyTorcher, new ProfessionTorcher().setIconHolder(iconTorcher).setUnlocalizedName("torcher").setRegistryName(keyTorcher));

    }

}
