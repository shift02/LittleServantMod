package littleservantmod.profession;

import java.util.Map;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.ProfessionBase;
import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ProfessionLSMBase extends ProfessionBase {

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
	public boolean hasOption(IServant servant) {
		return false;
	}

	@Override
	public IMode getDefaultMode(IServant servant) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Map<ResourceLocation, IMode> initModes(IServant servant) {
		return null;
	}

	@Override
	public IBehavior getDefaultBehavior(IServant servant) {
		return null;
	}

	@Override
	public Map<ResourceLocation, IBehavior> initBehavior(IServant servant) {
		return null;
	}

}
