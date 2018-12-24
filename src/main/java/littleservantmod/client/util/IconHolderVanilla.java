package littleservantmod.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * バニラにあるアイコンを保持するクラス
 * @author Shift02
 *
 */
public class IconHolderVanilla implements IIconHolder {

    private ResourceLocation iconResources;

    public IconHolderVanilla(ResourceLocation iconResources) {
        this.iconResources = iconResources;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon() {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iconResources.toString());
    }

}
