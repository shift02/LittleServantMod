package littleservantmod.client.gui.toptab;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.packet.MessageOpenGuiId;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TabInventory extends AbstractServantTab {

	public TabInventory(EntityLittleServant servant) {
		super(servant);
	}

	@Override
	public void onTabClicked() {

		OpenGuiEntityId id = new OpenGuiEntityId(this.servant);

		LSMPacketHandler.INSTANCE.sendToServer(new MessageOpenGuiId(0, id.getX()));

	}

	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Items.SUGAR);
	}

	@Override
	public String getTabName() {
		return "toptab.home.name";
	}

}
