package littleservantmod.profession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import littleservantmod.api.profession.IProfession;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * 職業関係の動作をまとめて処理するクラス
 * @author shift02
 *
 */
public class ProfessionDispatcher implements INBTSerializable<NBTTagCompound> {

	private IProfession[] professions;
	private Map<ResourceLocation, IProfession> professionsMap;
	private INBTSerializable<NBTBase>[] writers;
	private String[] names;

	/** 現在の職業 */
	private IProfession currentProfession = null;

	public ProfessionDispatcher(Map<ResourceLocation, IProfession> list) {

		this.initNBT(list);

	}

	/**NBT関係の初期化処理*/
	private void initNBT(Map<ResourceLocation, IProfession> list) {

		List<IProfession> lstCaps = Lists.newArrayList();
		List<INBTSerializable<NBTBase>> lstWriters = Lists.newArrayList();
		List<String> lstNames = Lists.newArrayList();
		HashMap<ResourceLocation, IProfession> mapRro = new HashMap<>();

		for (Map.Entry<ResourceLocation, IProfession> entry : list.entrySet()) {
			IProfession prov = entry.getValue();
			lstCaps.add(prov);
			mapRro.put(entry.getKey(), entry.getValue());
			if (prov instanceof INBTSerializable) {
				lstWriters.add((INBTSerializable<NBTBase>) prov);
				lstNames.add(entry.getKey().toString());
			}
		}

		professions = lstCaps.toArray(new IProfession[lstCaps.size()]);
		professionsMap = mapRro;
		writers = lstWriters.toArray(new INBTSerializable[lstWriters.size()]);
		names = lstNames.toArray(new String[lstNames.size()]);

	}

	public IProfession getDefaultProfession() {
		return professionsMap.get(ProfessionEventHandler.kyeUnemployed);
	}

	public IProfession getDefaultTamedProfession() {
		return professionsMap.get(ProfessionEventHandler.keyChores);
	}

	public IProfession getProfession(ResourceLocation resourceLocation) {

		if (!this.professionsMap.containsKey(resourceLocation)) {
			this.professionsMap.get(ProfessionEventHandler.kyeUnemployed);
		}

		return this.professionsMap.get(resourceLocation);

	}

	public void setCurrentProfession(IProfession profession) {
		this.currentProfession = profession;
	}

	// NBT
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		for (int x = 0; x < writers.length; x++) {
			nbt.setTag(names[x], writers[x].serializeNBT());
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		for (int x = 0; x < writers.length; x++) {
			if (nbt.hasKey(names[x])) {
				writers[x].deserializeNBT(nbt.getTag(names[x]));
			}
		}
	}

}
