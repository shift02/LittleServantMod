package littleservantmod;

import littleservantmod.api.profession.mode.ModeBasic;
import littleservantmod.api.profession.mode.ModeNone;
import littleservantmod.client.gui.inventory.GuiServantInventory;
import littleservantmod.client.gui.inventory.GuiServantProfession;
import littleservantmod.client.gui.inventory.GuiServantSelectBehavior;
import littleservantmod.client.gui.inventory.GuiServantSelectMode;
import littleservantmod.client.gui.inventory.GuiServantSelectProfession;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.EntityLittleServantBase;
import littleservantmod.inventory.ContainerServant;
import littleservantmod.inventory.ContainerServantProfession;
import littleservantmod.profession.DefaultProfessionEventHandler;
import littleservantmod.profession.mode.ModeEventHandler;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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
            return new ContainerServant(player.inventory, entity.getInventory(), entity);
        case 1:
            return new ContainerServantProfession(player.inventory, entity.getInventory(), entity);

        case 10:
            return new ContainerServantProfession(player.inventory, entity.getInventory(), entity);

        case 11:
            return new ContainerServantProfession(player.inventory, entity.getInventory(), entity);

        case 12:
            return new ContainerServantProfession(player.inventory, entity.getInventory(), entity);
        }

        return null;

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        EntityLittleServant entity = OpenGuiEntityId.getEntityFromXYZ(world, x, y, z);

        switch (ID) {
        case 0:
            return new GuiServantInventory(entity, player.inventory, new ContainerServant(player.inventory, entity.getInventory(), entity));
        case 1:
            return new GuiServantProfession(entity, player.inventory, new ContainerServantProfession(player.inventory, entity.getInventory(), entity));
        case 10:
            return new GuiServantSelectProfession(entity, player.inventory, new ContainerServantProfession(player.inventory, entity.getInventory(), entity));
        case 11:
            return new GuiServantSelectMode(entity, player.inventory, new ContainerServantProfession(player.inventory, entity.getInventory(), entity));
        case 12:
            return new GuiServantSelectBehavior(entity, player.inventory, new ContainerServantProfession(player.inventory, entity.getInventory(), entity));
        }

        return null;
    }

    public void openGui(EntityPlayer player, EntityLittleServantBase entity) {

        OpenGuiEntityId id = new OpenGuiEntityId(entity);

        player.openGui(LittleServantMod.instance, 0, player.world, id.getX(), 0, 0);

    }

    public String getBiomeName(Biome biome) {
        return "";
    }

    @SideOnly(Side.SERVER)
    public static class ServerProxy extends LSMProxy {

    }

    @SideOnly(Side.CLIENT)
    public static class ClientProxy extends LSMProxy {

        public static TextureAtlasSprite escort;
        public static TextureAtlasSprite free;

        @Override
        public EntityPlayer getClientPlayer() {
            return Minecraft.getMinecraft().player;
        }

        @Override
        public String getBiomeName(Biome biome) {
            return biome.getBiomeName();
        }

        @SubscribeEvent
        public void textureStitch(TextureStitchEvent.Pre event) {
            TextureMap textureMap = event.getMap();

            if (textureMap == Minecraft.getMinecraft().getTextureMapBlocks()) {

                //TODO この辺をもう少しスマートにしたい
                DefaultProfessionEventHandler.iconUnemployed.setIcon(
                        textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_unemployed")));

                DefaultProfessionEventHandler.iconChores.setIcon(
                        textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_chores")));

                ModeEventHandler.iconDefault.setIcon(
                        textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_default")));

                ModeNone.icon = textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_none"));

                ModeBasic.icon = textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_basic"));

                escort = textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_escort"));

                free = textureMap.registerSprite(new ResourceLocation(LittleServantMod.MOD_ID, "icons/icon_free"));

            }

        }

    }

}