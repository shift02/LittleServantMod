package littleservantmod.client.gui;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.mode.IMode;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ElementChangeModeCurrent extends ElementChangeMode {

	protected IMode now;

	protected GuiButton buttonOk;

	public ElementChangeModeCurrent(GuiContainer gui, int posX, int posY, IMode mode, IServant servant, GuiButton buttonOk) {
		super(gui, posX, posY, mode, servant);

		this.now = mode;

		this.buttonOk = buttonOk;

	}

	public void setMode(IMode mode) {
		this.mode = mode;

		this.buttonOk.enabled = !this.now.equals(this.mode);

	}

	public IMode getMode() {
		return this.mode;
	}

}
