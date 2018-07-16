package littleservantmod.client.gui;

import java.util.List;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.mode.IMode;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ElementChangeMode extends ElementIcon {

	protected IMode mode;

	protected IServant servant;

	public ElementChangeMode(GuiContainer gui, int posX, int posY, IMode mode, IServant servant) {
		super(gui, posX, posY);
		this.sizeX = 16;
		this.sizeY = 16;

		this.mode = mode;

		this.servant = servant;

	}

	@Override
	public void addTooltip(List<String> list) {
		list.add("sssss");
	}

	@Override
	public String getDisplayName() {
		return mode.getModeDisplayName(servant);
	}

	@Override
	public TextureAtlasSprite getIcon() {
		return this.mode.getIcon(servant);
	}

}
