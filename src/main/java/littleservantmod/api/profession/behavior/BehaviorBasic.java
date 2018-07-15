package littleservantmod.api.profession.behavior;

import littleservantmod.api.IServant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BehaviorBasic extends BehaviorBase {

	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite icon;

	public BehaviorBasic() {
	}

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
