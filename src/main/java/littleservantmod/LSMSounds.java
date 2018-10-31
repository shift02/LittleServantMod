package littleservantmod;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ServantSkin;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 効果音の関係を管理しているクラス
 * @author shift02
 */
@Mod.EventBusSubscriber
public class LSMSounds {

    public static SoundEvent getAmbientSound(EntityLittleServant servant) {

        if (servant.getServantSkin() == ServantSkin.SKIN_ALEX) {
            return ENTITY_LITTLEButler_AMBIENT;
        }

        return ENTITY_LITTLEMAID_AMBIENT;

        //return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    public static SoundEvent getHurtSound(EntityLittleServant servant, DamageSource damageSourceIn) {

        if (servant.getServantSkin() == ServantSkin.SKIN_ALEX) {
            return ENTITY_LITTLEButler_HURT;
        }

        return ENTITY_LITTLEMAID_HURT;
    }

    public static SoundEvent getDeathSound(EntityLittleServant servant) {

        if (servant.getServantSkin() == ServantSkin.SKIN_ALEX) {
            return ENTITY_LITTLEButler_DEATH;
        }

        return ENTITY_LITTLEMAID_DEATH;
    }

    //メイド
    public static final SoundEvent ENTITY_LITTLEMAID_AMBIENT = createSoundEvent("entity.little_maid.ambient");
    public static final SoundEvent ENTITY_LITTLEMAID_HURT = createSoundEvent("entity.little_maid.hurt");
    public static final SoundEvent ENTITY_LITTLEMAID_DEATH = createSoundEvent("entity.little_maid.death");

    //バトラー
    public static final SoundEvent ENTITY_LITTLEButler_AMBIENT = createSoundEvent("entity.little_butler.ambient");
    public static final SoundEvent ENTITY_LITTLEButler_HURT = createSoundEvent("entity.little_butler.hurt");
    public static final SoundEvent ENTITY_LITTLEButler_DEATH = createSoundEvent("entity.little_butler.death");

    private static SoundEvent createSoundEvent(String soundName) {
        final ResourceLocation soundID = new ResourceLocation(LittleServantMod.MOD_ID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {

        event.getRegistry().registerAll(
                ENTITY_LITTLEMAID_AMBIENT,
                ENTITY_LITTLEMAID_HURT,
                ENTITY_LITTLEMAID_DEATH,

                ENTITY_LITTLEButler_AMBIENT,
                ENTITY_LITTLEButler_HURT,
                ENTITY_LITTLEButler_DEATH);

    }

}
