package littleservantmod.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;

public class GuiLSMButton extends GuiButton {

	protected GuiServantBase gui;

	public GuiLSMButton(GuiServantBase gui, int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);

		this.gui = gui;
	}

	public GuiLSMButton(GuiServantBase gui, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);

		this.gui = gui;
	}

	protected void renderHoveredToolTip(int mouseX, int mouseY) {

	}

}
