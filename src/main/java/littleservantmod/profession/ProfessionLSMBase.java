package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.ProfessionBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProfessionLSMBase extends ProfessionBase {

	protected IconHolder iconHolder;

	public IconHolder getIconHolder() {
		return iconHolder;
	}

	public ProfessionLSMBase setIconHolder(IconHolder iconHolder) {
		this.iconHolder = iconHolder;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon(IServant servant) {

		if (this.iconHolder == null || this.iconHolder.getIcon() == null) {
			//Missing
			return net.minecraft.client.Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
		}

		return this.iconHolder.getIcon();
	}

	@Override
	public boolean isEnableProfession(IServant servant) {
		return true;
	}

	@Override
	public boolean hasMode(IServant servant) {
		return true;
	}

	@Override
	public boolean hasOption(IServant servant) {
		return false;
	}

}
