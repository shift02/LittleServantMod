package littleservantmod;

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

		switch (ID) {
		case 200:
			//return new ContainerPlayerNext(player.inventory, player);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		switch (ID) {
		case 200:
			//return new GuiInventoryNext(player);
		}

		return null;
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