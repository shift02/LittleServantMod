package littleservantmod.profession;

import java.util.Map;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.ProfessionBase;
import littleservantmod.api.profession.mode.IMode;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
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
		return false;
	}

	@Override
	public boolean hasOption(IServant servant) {
		return false;
	}

	@Override
	public Map<ResourceLocation, IMode> initModes(IServant servant) {
		return null;
	}

	@Override
	public IMode getDefaultMode(IServant servant) {
		return null;
	}

}
