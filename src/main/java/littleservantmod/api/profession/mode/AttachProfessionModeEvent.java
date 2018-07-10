package littleservantmod.api.profession.mode;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

import littleservantmod.api.IServant;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * サーヴァントの職業にモードを追加するイベント
 * @author shift02
 */
public class AttachProfessionModeEvent extends Event {

	private final IServant servant;
	private final Map<ResourceLocation, IMode> modes = Maps.newLinkedHashMap();
	private final Map<ResourceLocation, IMode> view = Collections.unmodifiableMap(modes);

	private final Map<ResourceLocation, IMode> defaultView;

	public AttachProfessionModeEvent(IServant servant, Map<ResourceLocation, IMode> defaultModes) {
		this.servant = servant;
		this.defaultView = Collections.unmodifiableMap(defaultModes);
	}

	public IServant getServant() {
		return this.servant;
	}

	public void addProfession(ResourceLocation key, IMode mode) {
		if (modes.containsKey(key)) throw new IllegalStateException("Duplicate IMode Key: " + key + " " + mode);
		if (mode.getRegistryName() == null) throw new IllegalStateException("Not found RegistryName: " + key + " " + mode);
		this.modes.put(key, mode);
	}

	public Map<ResourceLocation, IMode> getModes() {
		return view;
	}

	public Map<ResourceLocation, IMode> getDefaultModes() {
		return defaultView;
	}

}
