package littleservantmod.api.event.entity.item;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * サーヴァントが自主的にアイテムを捨てた時に呼ばれるEvent
 * (使うか不明)
 * */
@Cancelable
public class ItemTossEvent extends ItemEvent {

	private final EntityLittleServant servant;

	public ItemTossEvent(EntityItem entityItem, EntityLittleServant player) {
		super(entityItem);
		this.servant = player;
	}

	public EntityLittleServant getPlayer() {
		return servant;
	}
}