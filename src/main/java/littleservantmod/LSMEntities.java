package littleservantmod;

import littleservantmod.client.renderer.entity.RenderEntityLittleServant;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class LSMEntities {

    private final FMLPreInitializationEvent event;

    public LSMEntities(FMLPreInitializationEvent event) {
        this.event = event;
    }

    public void registerEntities() {

        int trackingRange = 80;
        int updateFrequency = 3;
        boolean sendVelocityUpdates = true;
        EntityRegistry.registerModEntity(new ResourceLocation(LittleServantMod.MOD_ID, "little_servant"), EntityLittleServant.class,
                "little_servant", 0, LittleServantMod.instance, trackingRange, updateFrequency, sendVelocityUpdates, 0xffffff, 0xFF0000);

        if (!event.getSide().isServer()) {
            RenderingRegistry.registerEntityRenderingHandler(EntityLittleServant.class, new IRenderFactory() {
                @Override
                public Render createRenderFor(RenderManager manager) {
                    return new RenderEntityLittleServant(manager);
                }
            });
        }

    }

}
