package littleservantmod.api.profession.behavior;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

import littleservantmod.api.IServant;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * サーヴァントの職業に振る舞いを追加するイベント
 * @author shift02
 */
public class AttachProfessionBehaviorEvent extends Event {

	private final IServant servant;
	private final Map<ResourceLocation, IBehavior> behaviors = Maps.newLinkedHashMap();
	private final Map<ResourceLocation, IBehavior> view = Collections.unmodifiableMap(behaviors);

	private final Map<ResourceLocation, IBehavior> defaultView;

	public AttachProfessionBehaviorEvent(IServant servant, Map<ResourceLocation, IBehavior> defaultBehaviors) {
		this.servant = servant;
		this.defaultView = Collections.unmodifiableMap(defaultBehaviors);
	}

	public IServant getServant() {
		return this.servant;
	}

	public void addProfession(ResourceLocation key, IBehavior behavior) {
		if (behaviors.containsKey(key)) throw new IllegalStateException("Duplicate IBehavior Key: " + key + " " + behavior);
		if (behavior.getRegistryName() == null) throw new IllegalStateException("Not found RegistryName: " + key + " " + behavior);
		this.behaviors.put(key, behavior);
	}

	public Map<ResourceLocation, IBehavior> getBehaviors() {
		return view;
	}

	public Map<ResourceLocation, IBehavior> getDefaultBehaviors() {
		return defaultView;
	}

}
