package littleservantmod;

import littleservantmod.client.gui.inventory.GuiServantInventory;
import littleservantmod.client.gui.inventory.GuiServantProfession;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.EntityLittleServantBase;
import littleservantmod.inventory.ContainerServant;
import littleservantmod.inventory.ContainerServantProfession;
import littleservantmod.profession.ProfessionEventHandler;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
			return new ContainerServant(player.inventory, entity.inventory, entity);
		case 1:
			return new ContainerServantProfession(player.inventory, entity.inventory, entity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		EntityLittleServant entity = OpenGuiEntityId.getEntityFromXYZ(world, x, y, z);

		switch (ID) {
		case 0:
			return new GuiServantInventory(entity, player.inventory, new ContainerServant(player.inventory, entity.inventory, entity));
		case 1:
			return new GuiServantProfession(entity, player.inventory, new ContainerServantProfession(player.inventory, entity.inventory, entity));
		}

		return null;
	}

	public void openGui(EntityPlayer player, EntityLittleServantBase entity) {

		OpenGuiEntityId id = new OpenGuiEntityId(entity);

		player.openGui(LittleServantMod.instance, 1, player.world, id.getX(), 0, 0);

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

		@SubscribeEvent
		public void textureStitch(TextureStitchEvent.Pre event) {
			TextureMap textureMap = event.getMap();

			if (textureMap == Minecraft.getMinecraft().getTextureMapBlocks()) {

				//TODO この辺をもう少しスマートにしたい
				ProfessionEventHandler.iconUnemployed.setIcon(
						textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_unemployed")));

				ProfessionEventHandler.iconChores.setIcon(
						textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_chores")));

			}

		}

	}

}