package littleservantmod.client.gui;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.behavior.IBehavior;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ElementChangeBehaviorCurrent extends ElementChangeBehavior {

	protected IBehavior now;

	protected GuiButton buttonOk;

	public ElementChangeBehaviorCurrent(GuiContainer gui, int posX, int posY, IBehavior behavior, IServant servant, GuiButton buttonOk) {
		super(gui, posX, posY, behavior, servant);

		this.now = behavior;

		this.buttonOk = buttonOk;

	}

	public void setBehavior(IBehavior behavior) {
		this.behavior = behavior;

		this.buttonOk.enabled = !this.now.equals(this.behavior);

	}

	public IBehavior getBehavior() {
		return this.behavior;
	}

}
