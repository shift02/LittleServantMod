package littleservantmod.client.gui;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.mode.IMode;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ElementChangeModeSelect extends ElementChangeMode {

	protected ElementChangeModeCurrent current;

	public ElementChangeModeSelect(GuiContainer gui, int posX, int posY, IMode mode, IServant servant, ElementChangeModeCurrent current) {
		super(gui, posX, posY, mode, servant);

		this.current = current;

	}

	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		current.setMode(this.mode);
		return true;

	}

}