package littleservantmod.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * イベントを使って動的にIconを登録するクラス
 * @author Shift02
 *
 */
public class IconHolderDynamic implements IIconHolder {

    @SideOnly(Side.CLIENT)
    protected TextureAtlasSprite icon;

    protected ResourceLocation source;

    public IconHolderDynamic(ResourceLocation source) {

        this.source = source;
        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void textureStitch(TextureStitchEvent.Pre event) {

        TextureMap textureMap = event.getMap();

        if (textureMap == Minecraft.getMinecraft().getTextureMapBlocks()) {
            icon = textureMap.registerSprite(source);
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon() {
        return icon;
    }

}
