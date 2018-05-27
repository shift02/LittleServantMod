package littleservantmod.api.profession;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

import littleservantmod.api.IServant;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * サーヴァントに職業を追加するためのイベント
 * @author shift02
 */
public class AttachProfessionEvent extends Event {

	private final IServant servant;
	private final Map<ResourceLocation, IProfession> professions = Maps.newLinkedHashMap();
	private final Map<ResourceLocation, IProfession> view = Collections.unmodifiableMap(professions);

	public AttachProfessionEvent(IServant servant) {
		this.servant = servant;
	}

	public IServant getServant() {
		return this.servant;
	}

	public void addProfession(ResourceLocation key, IProfession profession) {
		if (professions.containsKey(key)) throw new IllegalStateException("Duplicate IProfession Key: " + key + " " + profession);
		if (profession.getRegistryName() == null) throw new IllegalStateException("Not found RegistryName: " + key + " " + profession);
		this.professions.put(key, profession);
	}

	public Map<ResourceLocation, IProfession> getProfessions() {
		return view;
	}

}
