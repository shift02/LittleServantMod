package littleservantmod.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageOpenGuiId implements IMessage {

	private NBTTagCompound data;

	public MessageOpenGuiId() {

	}

	public MessageOpenGuiId(int id, int x) {

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("gui", id);
		nbt.setInteger("x", x);
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
