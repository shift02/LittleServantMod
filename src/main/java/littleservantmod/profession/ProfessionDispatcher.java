package littleservantmod.profession;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import littleservantmod.api.profession.IProfession;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * 職業関係の動作をまとめて処理するクラス
 * @author shift02
 *
 */
public class ProfessionDispatcher {

	private ICapabilityProvider[] caps;
	private INBTSerializable<NBTBase>[] writers;
	private String[] names;

	public ProfessionDispatcher(Map<ResourceLocation, IProfession> list) {

		List<IProfession> lstCaps = Lists.newArrayList();
		List<INBTSerializable<NBTBase>> lstWriters = Lists.newArrayList();
		List<String> lstNames = Lists.newArrayList();

		for (Map.Entry<ResourceLocation, IProfession> entry : list.entrySet()) {
			IProfession prov = entry.getValue();
			lstCaps.add(prov);
			if (prov instanceof INBTSerializable) {
				lstWriters.add((INBTSerializable<NBTBase>) prov);
				lstNames.add(entry.getKey().toString());
			}
		}

		caps = lstCaps.toArray(new ICapabilityProvider[lstCaps.size()]);
		writers = lstWriters.toArray(new INBTSerializable[lstWriters.size()]);
		names = lstNames.toArray(new String[lstNames.size()]);

	}

}
