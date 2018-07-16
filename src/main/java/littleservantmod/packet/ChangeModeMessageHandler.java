package littleservantmod.packet;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ChangeModeMessageHandler implements IMessageHandler<MessageChangeMode, IMessage> {

	@Override
	public IMessage onMessage(MessageChangeMode message, MessageContext ctx) {

		EntityPlayerMP p = ctx.getServerHandler().player;

		int entityID = message.getData().getInteger("entity");
		ResourceLocation modeID = new ResourceLocation(message.getData().getString("mode"));

		EntityLittleServant entity = (EntityLittleServant) p.getServerWorld().getEntityByID(entityID);

		entity.changeMode(modeID);

		//CustomPlayerData data = EntityPlayerManager.getCustomPlayerData(p);

		//data.loadNBTData(message.getData());

		//System.out.println("AAAAA" + message.getData().getInteger("gui"));

		return null;

	}

}