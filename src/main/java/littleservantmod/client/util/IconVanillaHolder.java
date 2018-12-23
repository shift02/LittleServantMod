package littleservantmod.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

/**
 * バニラにあるアイコンを保持するクラス
 * @author Shift02
 *
 */
public class IconVanillaHolder implements IIconHolder {

    private ResourceLocation iconResources;

    public IconVanillaHolder(ResourceLocation iconResources) {
        this.iconResources = iconResources;
    }

    @Override
    public TextureAtlasSprite getIcon() {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iconResources.toString());
    }

}
