package littleservantmod.packet;

import io.netty.buffer.ByteBuf;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageChangeMode implements IMessage {

	private NBTTagCompound data;

	public MessageChangeMode() {

	}

	public MessageChangeMode(EntityLittleServant servant, IMode mode) {

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("entity", servant.getEntityId());
		nbt.setString("mode", mode.getRegistryName().toString());
		this.setData(nbt);

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		setData(ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, getData());
	}

	public NBTTagCompound getData() {
		return data;
	}

	public void setData(NBTTagCompound data) {
		this.data = data;
	}

}
