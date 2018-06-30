package littleservantmod.packet;

import io.netty.buffer.ByteBuf;
import littleservantmod.api.profession.IProfession;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageChangeProfession implements IMessage {

	private NBTTagCompound data;

	public MessageChangeProfession() {

	}

	public MessageChangeProfession(EntityLittleServant servant, IProfession profession) {

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("entity", servant.getEntityId());
		nbt.setString("profession", profession.getRegistryName().toString());
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
