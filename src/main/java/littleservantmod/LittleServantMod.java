package littleservantmod;

import org.apache.logging.log4j.Logger;

import littleservantmod.client.renderer.entity.RenderEntityLittleServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.profession.ProfessionChores;
import littleservantmod.profession.ProfessionUnemployed;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		int trackingRange = 80;
		int updateFrequency = 3;
		boolean sendVelocityUpdates = true;
		EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "little_servant"), EntityLittleServant.class,
				"little_servant", 0, this, trackingRange, updateFrequency, sendVelocityUpdates, 0xffffff, 0xFF0000);
		//EntityRegistry.addSpawn(SampleEntity.class, 8, 4, 4, EnumCreatureType.MONSTER, BiomeGenBase.getBiome(0));
		if (event.getSide().isServer()) {
			return;
		}
		RenderingRegistry.registerEntityRenderingHandler(EntityLittleServant.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderEntityLittleServant(manager);//new SampleEntityRender(manager);
			}
		});

		LSMProxy.getProxy().fmlPreInit();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, LSMProxy.getProxy());

		//職業
		ProfessionManager.getInstance().registProfession(new ProfessionUnemployed());
		ProfessionManager.getInstance().registProfession(new ProfessionChores());

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// some example code
		logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

}
