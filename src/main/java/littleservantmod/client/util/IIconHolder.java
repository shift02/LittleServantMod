package littleservantmod.client.util;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TextureAtlasSpriteを保持するクラス<br />
 * なぜワンクッショ入れているのか不明
 * @author Shift02
 *
 */
public interface IIconHolder {

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon();

}
