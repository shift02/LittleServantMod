package littleservantmod;

import org.apache.logging.log4j.Logger;

import littleservantmod.api.LittleServantModAPI;
import littleservantmod.client.renderer.entity.RenderEntityLittleServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.profession.DefaultProfessionEventHandler;
import littleservantmod.profession.ProfessionManager;
import littleservantmod.profession.ServantToolManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = LittleServantMod.MOD_ID, name = LittleServantMod.MOD_NAME, version = LittleServantMod.MOD_VERSION, useMetadata = true)
public class LittleServantMod {

    /** ModID文字列 */
    public static final String MOD_ID = "littleservantmod";
    /** MOD名称 */
    public static final String MOD_NAME = "LittleServantMod";
    /** MODのバージョン */
    public static final String MOD_VERSION = "0.0.11";

    private static Logger logger;

    public static boolean isDebug = false;

    @Instance(MOD_ID)
    public static LittleServantMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        isDebug = event.getSourceFile().isDirectory() && (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

        LittleServantModAPI.register = new LSMRegister();

        int trackingRange = 80;
        int updateFrequency = 3;
        boolean sendVelocityUpdates = true;
        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "little_servant"), EntityLittleServant.class,
                "little_servant", 0, this, trackingRange, updateFrequency, sendVelocityUpdates, 0xffffff, 0xFF0000);

        if (!event.getSide().isServer()) {
            RenderingRegistry.registerEntityRenderingHandler(EntityLittleServant.class, new IRenderFactory() {
                @Override
                public Render createRenderFor(RenderManager manager) {
                    return new RenderEntityLittleServant(manager);
                }
            });
        }

        LSMProxy.getProxy().fmlPreInit();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, LSMProxy.getProxy());
        MinecraftForge.EVENT_BUS.register(LSMProxy.getProxy());

        LSMPacketHandler.init(event);

        LittleServantModAPI.professionManager = ProfessionManager.getInstance();

        //職業
        MinecraftForge.EVENT_BUS.register(new DefaultProfessionEventHandler());
        //ProfessionManager.getInstance().registProfession(new ProfessionUnemployed());
        //ProfessionManager.getInstance().registProfession(new ProfessionChores());

        //Minecraft.getMinecraft().getResourceManager()

        //new FolderResourcePack(null)

        LittleServantModAPI.servantToolManager = ServantToolManager.getInstance();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        LSMSugarItems.registrySugarItem();

        /*
        String name = "Loading Resource";
        ProgressBar bar = ProgressManager.push(name, 20);
        
        ResourceLocation test = new ResourceLocation("military_custom" + "@" + MOD_ID, "test_foo");
        
        for (int i = 0; i < 20; i++) {
        	try {
        		Thread.sleep(1000);
        		bar.step(test.toString());
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        }
        
        ProgressManager.pop(bar);
        */

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        //サーヴァントのスポーン設定
        LSMBiomes.registerBiomesSpawn(logger);
    }

}
