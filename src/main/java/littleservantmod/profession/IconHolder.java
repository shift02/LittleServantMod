package littleservantmod.profession;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IconHolder {

	@SideOnly(Side.CLIENT)
	protected TextureAtlasSprite icon;

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon() {
		return icon;
	}

	@SideOnly(Side.CLIENT)
	public void setIcon(TextureAtlasSprite icon) {
		this.icon = icon;
	}

}
