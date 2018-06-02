package littleservantmod;

import littleservantmod.client.gui.inventory.GuiServantInventory;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.EntityLittleServantBase;
import littleservantmod.inventory.ContainerServant;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LSMProxy implements IGuiHandler {

	@SidedProxy(modId = LittleServantMod.MOD_ID)
	private static LSMProxy proxy;

	public static LSMProxy getProxy() {
		return proxy;
	}

	public void fmlPreInit() {

	}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		EntityLittleServant entity = OpenGuiEntityId.getEntityFromXYZ(world, x, y, z);

		switch (ID) {
		case 0:
			return new ContainerServant(player.inventory, null, entity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		EntityLittleServant entity = OpenGuiEntityId.getEntityFromXYZ(world, x, y, z);

		switch (ID) {
		case 0:
			return new GuiServantInventory(entity, player.inventory, new ContainerServant(player.inventory, null, entity));
		}

		return null;
	}

	public void openGui(EntityPlayer player, EntityLittleServantBase entity) {

		OpenGuiEntityId id = new OpenGuiEntityId(entity);

		player.openGui(LittleServantMod.instance, 0, player.world, id.getX(), 0, 0);

	}

	@SideOnly(Side.SERVER)
	public static class ServerProxy extends LSMProxy {
	}

	@SideOnly(Side.CLIENT)
	public static class ClientProxy extends LSMProxy {

		@Override
		public EntityPlayer getClientPlayer() {
			return Minecraft.getMinecraft().player;
		}

	}

}