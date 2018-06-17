package littleservantmod;

import org.apache.logging.log4j.Logger;

import littleservantmod.api.LittleServantModAPI;
import littleservantmod.client.renderer.entity.RenderEntityLittleServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.profession.ProfessionEventHandler;
import littleservantmod.profession.ProfessionManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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
	public static final String MOD_VERSION = "0.0.1";

	private static Logger logger;

	@Instance(MOD_ID)
	public static LittleServantMod instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		int trackingRange = 80;
		int updateFrequency = 3;
		boolean sendVelocityUpdates = true;
		EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "little_servant"), EntityLittleServant.class,
				"little_servant", 0, this, trackingRange, updateFrequency, sendVelocityUpdates, 0xffffff, 0xFF0000);
		//EntityRegistry.addSpawn(SampleEntity.class, 8, 4, 4, EnumCreatureType.MONSTER, BiomeGenBase.getBiome(0));
		if (!event.getSide().isServer()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityLittleServant.class, new IRenderFactory() {
				@Override
				public Render createRenderFor(RenderManager manager) {
					return new RenderEntityLittleServant(manager);//new SampleEntityRender(manager);
				}
			});
		}

		LSMProxy.getProxy().fmlPreInit();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, LSMProxy.getProxy());
		MinecraftForge.EVENT_BUS.register(LSMProxy.getProxy());

		LSMPacketHandler.init(event);

		LittleServantModAPI.professionManager = ProfessionManager.getInstance();

		//職業
		MinecraftForge.EVENT_BUS.register(new ProfessionEventHandler());
		//ProfessionManager.getInstance().registProfession(new ProfessionUnemployed());
		//ProfessionManager.getInstance().registProfession(new ProfessionChores());

		//Minecraft.getMinecraft().getResourceManager()

		//new FolderResourcePack(null)

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// some example code
		logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

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

}
