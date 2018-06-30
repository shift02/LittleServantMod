package littleservantmod.packet;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ChangeProfessionMessageHandler implements IMessageHandler<MessageChangeProfession, IMessage> {

	@Override
	public IMessage onMessage(MessageChangeProfession message, MessageContext ctx) {

		EntityPlayerMP p = ctx.getServerHandler().player;

		int entityID = message.getData().getInteger("entity");
		ResourceLocation professionID = new ResourceLocation(message.getData().getString("profession"));

		EntityLittleServant entity = (EntityLittleServant) p.getServerWorld().getEntityByID(entityID);

		entity.changeProfession(professionID);

		//CustomPlayerData data = EntityPlayerManager.getCustomPlayerData(p);

		//data.loadNBTData(message.getData());

		//System.out.println("AAAAA" + message.getData().getInteger("gui"));

		return null;

	}

}