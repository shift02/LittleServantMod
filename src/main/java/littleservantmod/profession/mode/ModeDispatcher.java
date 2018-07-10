package littleservantmod.profession.mode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import littleservantmod.api.profession.mode.IMode;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class ModeDispatcher implements INBTSerializable<NBTTagCompound> {

	private IMode[] modes;
	private Map<ResourceLocation, IMode> modesMap;
	private INBTSerializable<NBTBase>[] writers;
	private String[] names;

	/** 現在の職業のモード */
	private IMode currentMode = null;

	public ModeDispatcher(Map<ResourceLocation, IMode> defaultModes, Map<ResourceLocation, IMode> list) {

		for (Map.Entry<ResourceLocation, IMode> entry : list.entrySet()) {
			defaultModes.put(entry.getKey(), entry.getValue());
		}

		this.initNBT(defaultModes);

	}

	/**NBT関係の初期化処理*/
	private void initNBT(Map<ResourceLocation, IMode> list) {

		List<IMode> lstCaps = Lists.newArrayList();
		List<INBTSerializable<NBTBase>> lstWriters = Lists.newArrayList();
		List<String> lstNames = Lists.newArrayList();
		HashMap<ResourceLocation, IMode> mapRro = new HashMap<>();

		for (Map.Entry<ResourceLocation, IMode> entry : list.entrySet()) {
			IMode prov = entry.getValue();
			lstCaps.add(prov);
			mapRro.put(entry.getKey(), entry.getValue());
			if (prov instanceof INBTSerializable) {
				lstWriters.add((INBTSerializable<NBTBase>) prov);
				lstNames.add(entry.getKey().toString());
			}
		}

		modes = lstCaps.toArray(new IMode[lstCaps.size()]);
		modesMap = mapRro;
		writers = lstWriters.toArray(new INBTSerializable[lstWriters.size()]);
		names = lstNames.toArray(new String[lstNames.size()]);

	}

	public IMode[] getModes() {
		return modes;
	}

	public IMode getDefaultMode() {
		return modesMap.get(ModeEventHandler.kyeDefault);
	}

	public IMode getMode(ResourceLocation resourceLocation) {

		if (!this.modesMap.containsKey(resourceLocation)) {
			this.modesMap.get(ModeEventHandler.kyeDefault);
		}

		return this.modesMap.get(resourceLocation);

	}

	public void setCurrentMode(IMode mode) {
		this.currentMode = mode;
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