package littleservantmod.packet;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ChangeBehaviorMessageHandler implements IMessageHandler<MessageChangeBehavior, IMessage> {

	@Override
	public IMessage onMessage(MessageChangeBehavior message, MessageContext ctx) {

		EntityPlayerMP p = ctx.getServerHandler().player;

		int entityID = message.getData().getInteger("entity");
		ResourceLocation behaviorID = new ResourceLocation(message.getData().getString("behavior"));

		EntityLittleServant entity = (EntityLittleServant) p.getServerWorld().getEntityByID(entityID);

		entity.changeBehavior(behaviorID);

		//CustomPlayerData data = EntityPlayerManager.getCustomPlayerData(p);

		//data.loadNBTData(message.getData());

		//System.out.println("AAAAA" + message.getData().getInteger("gui"));

		return null;

	}

}