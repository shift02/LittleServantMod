package littleservantmod.client.gui;

import java.util.List;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.behavior.IBehavior;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ElementChangeBehavior extends ElementIcon {

	protected IBehavior behavior;

	protected IServant servant;

	public ElementChangeBehavior(GuiContainer gui, int posX, int posY, IBehavior behavior, IServant servant) {
		super(gui, posX, posY);
		this.sizeX = 16;
		this.sizeY = 16;

		this.behavior = behavior;

		this.servant = servant;

	}

	@Override
	public void addTooltip(List<String> list) {
		list.add("sssss");
	}

	@Override
	public String getDisplayName() {
		return behavior.getBehaviorDisplayName(servant);
	}

	@Override
	public TextureAtlasSprite getIcon() {
		return this.behavior.getIcon(servant);
	}

}
