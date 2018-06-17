package littleservantmod.packet;

import littleservantmod.LittleServantMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenGuiIdMessageHandler implements IMessageHandler<MessageOpenGuiId, IMessage> {

	@Override
	public IMessage onMessage(MessageOpenGuiId message, MessageContext ctx) {

		EntityPlayerMP p = ctx.getServerHandler().player;

		//CustomPlayerData data = EntityPlayerManager.getCustomPlayerData(p);

		//data.loadNBTData(message.getData());

		//System.out.println("AAAAA" + message.getData().getInteger("gui"));

		int i = message.getData().getInteger("gui");
		int x = message.getData().getInteger("x");

		p.openGui(LittleServantMod.instance, i, p.world, x, (int) p.posY, (int) p.posZ);

		return null;

	}

}