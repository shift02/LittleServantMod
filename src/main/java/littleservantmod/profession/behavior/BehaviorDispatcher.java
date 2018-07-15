package littleservantmod.profession.behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import littleservantmod.api.profession.behavior.IBehavior;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class BehaviorDispatcher implements INBTSerializable<NBTTagCompound> {

	private IBehavior[] behaviors;
	private Map<ResourceLocation, IBehavior> behaviorsMap;
	private INBTSerializable<NBTBase>[] writers;
	private String[] names;

	/** 現在の職業のモード */
	private IBehavior currentBehavior = null;

	public BehaviorDispatcher(Map<ResourceLocation, IBehavior> defaultBehaviors, Map<ResourceLocation, IBehavior> list) {

		for (Map.Entry<ResourceLocation, IBehavior> entry : list.entrySet()) {
			defaultBehaviors.put(entry.getKey(), entry.getValue());
		}

		this.initNBT(defaultBehaviors);

	}

	/**NBT関係の初期化処理*/
	private void initNBT(Map<ResourceLocation, IBehavior> list) {

		List<IBehavior> lstCaps = Lists.newArrayList();
		List<INBTSerializable<NBTBase>> lstWriters = Lists.newArrayList();
		List<String> lstNames = Lists.newArrayList();
		HashMap<ResourceLocation, IBehavior> mapRro = new HashMap<>();

		for (Map.Entry<ResourceLocation, IBehavior> entry : list.entrySet()) {
			IBehavior prov = entry.getValue();
			lstCaps.add(prov);
			mapRro.put(entry.getKey(), entry.getValue());
			if (prov instanceof INBTSerializable) {
				lstWriters.add((INBTSerializable<NBTBase>) prov);
				lstNames.add(entry.getKey().toString());
			}
		}

		behaviors = lstCaps.toArray(new IBehavior[lstCaps.size()]);
		behaviorsMap = mapRro;
		writers = lstWriters.toArray(new INBTSerializable[lstWriters.size()]);
		names = lstNames.toArray(new String[lstNames.size()]);

	}

	public IBehavior[] getBehaviors() {
		return behaviors;
	}

	/*
	public IBehavior getDefaultBehavior() {
		return behaviorsMap.get(BehaviorEventHandler.kyeDefault);
	}*/

	public IBehavior getBehavior(ResourceLocation resourceLocation) {

		/*if (!this.behaviorsMap.containsKey(resourceLocation)) {
			this.behaviorsMap.get(BehaviorEventHandler.kyeDefault);
		}*/

		return this.behaviorsMap.get(resourceLocation);

	}

	public void setCurrentBehavior(IBehavior behavior) {
		this.currentBehavior = behavior;
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