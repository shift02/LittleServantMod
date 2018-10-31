package littleservantmod;

import littleservantmod.entity.EntityLittleServant;
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

        return ENTITY_LITTLEMADE_AMBIENT;

        //return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    public static SoundEvent getHurtSound(EntityLittleServant servant, DamageSource damageSourceIn) {
        return ENTITY_LITTLEMADE_HURT;
    }

    public SoundEvent getDeathSound(EntityLittleServant servant) {
        return ENTITY_LITTLEMADE_DEATH;
    }

    //メイド
    public static final SoundEvent ENTITY_LITTLEMADE_AMBIENT = createSoundEvent("entity.little_maid.ambient");
    public static final SoundEvent ENTITY_LITTLEMADE_HURT = createSoundEvent("entity.little_maid.hurt");
    public static final SoundEvent ENTITY_LITTLEMADE_DEATH = createSoundEvent("entity.little_maid.death");

    private static SoundEvent createSoundEvent(String soundName) {
        final ResourceLocation soundID = new ResourceLocation(LittleServantMod.MOD_ID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {

        event.getRegistry().registerAll(
                ENTITY_LITTLEMADE_AMBIENT);

    }

}
