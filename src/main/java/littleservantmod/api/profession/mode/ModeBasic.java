package littleservantmod.api.profession.mode;

import littleservantmod.api.IServant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 基本のモード <br />
 * モードが一つだけの時に使う
 * @author shift02
 */
public class ModeBasic extends ModeBase {

	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite icon;

	@Override
	public boolean isEnableMode(IServant servant) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon(IServant servant) {

		if (icon == null) {
			//Missing
			return net.minecraft.client.Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
		}

		return icon;
	}

}
