package littleservantmod.profession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.IProfession;
import littleservantmod.api.profession.mode.AttachProfessionModeEvent;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.profession.mode.ModeDispatcher;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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

	private Map<ResourceLocation, ModeDispatcher> modeDispatcher = null;

	public ProfessionDispatcher(IServant servant, Map<ResourceLocation, IProfession> list) {

		this.initNBT(list);
		this.initModes(servant, list);

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

	//モードの初期化
	public void initModes(IServant servant, Map<ResourceLocation, IProfession> list) {

		modeDispatcher = Maps.newLinkedHashMap();

		for (Map.Entry<ResourceLocation, IProfession> entry : list.entrySet()) {

			modeDispatcher.put(entry.getKey(), gatProfessionModes(servant, entry.getValue()));

		}

	}

	public ModeDispatcher gatProfessionModes(IServant servant, IProfession profession) {

		Map<ResourceLocation, IMode> modes = profession.initModes(servant);
		if (modes == null) {
			modes = Maps.newLinkedHashMap();
		}

		AttachProfessionModeEvent event = new AttachProfessionModeEvent(servant, modes);
		MinecraftForge.EVENT_BUS.post(event);

		return new ModeDispatcher(modes, event.getModes());

	}

	public IProfession[] getProfessions() {
		return professions;
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

	public IMode getMode(ResourceLocation resourceProfessionLocation, ResourceLocation resourceLocation) {

		return modeDispatcher.get(resourceProfessionLocation).getMode(resourceLocation);

	}

	// NBT
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();

		//Profession
		NBTTagCompound nbtProfession = new NBTTagCompound();

		nbt.setTag("Profession", nbtProfession);

		for (int x = 0; x < writers.length; x++) {
			nbtProfession.setTag(names[x], writers[x].serializeNBT());
		}

		//Mode
		NBTTagCompound nbtMode = new NBTTagCompound();
		nbt.setTag("Mode", nbtMode);

		for (Map.Entry<ResourceLocation, ModeDispatcher> entry : modeDispatcher.entrySet()) {
			nbtMode.setTag(entry.getKey().toString(), entry.getValue().serializeNBT());
		}

		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

		//Profession
		NBTTagCompound nbtProfession = nbt.getCompoundTag("Profession");

		for (int x = 0; x < writers.length; x++) {
			if (nbtProfession.hasKey(names[x])) {
				writers[x].deserializeNBT(nbtProfession.getTag(names[x]));
			}
		}

		//Mode
		NBTTagCompound nbtMode = nbt.getCompoundTag("Mode");
		for (Map.Entry<ResourceLocation, ModeDispatcher> entry : modeDispatcher.entrySet()) {
			if (nbtMode.hasKey(entry.getKey().toString())) {
				entry.getValue().deserializeNBT(nbtMode.getCompoundTag(entry.getKey().toString()));
			}
		}

	}

}
