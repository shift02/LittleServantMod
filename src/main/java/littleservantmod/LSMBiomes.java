package littleservantmod;

import java.util.Iterator;

import org.apache.logging.log4j.Logger;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class LSMBiomes {

    public static void registerBiomesSpawn(Logger logger) {

        Iterator<Biome> biomeIterator = Biome.REGISTRY.iterator();
        while (biomeIterator.hasNext()) {
            Biome biome = biomeIterator.next();

            if (biome != null && canSpawn(biome)) {
                EntityRegistry.addSpawn(EntityLittleServant.class, 7, 2, 6, EnumCreatureType.CREATURE, biome);
                logger.info("Registering spawn in " + LSMProxy.getProxy().getBiomeName(biome));
            }
        }
    }

    private static boolean canSpawn(Biome biome) {

        //伝統　砂漠で探す
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) return true;

        //if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT))return false;
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)) return false;

        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) return false;
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) return false;

        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.VOID)) return false;

        return true;
    }

}
