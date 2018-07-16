package littleservantmod.client.gui;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.behavior.IBehavior;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ElementChangeBehaviorSelect extends ElementChangeBehavior {

	protected ElementChangeBehaviorCurrent current;

	public ElementChangeBehaviorSelect(GuiContainer gui, int posX, int posY, IBehavior behavior, IServant servant, ElementChangeBehaviorCurrent current) {
		super(gui, posX, posY, behavior, servant);

		this.current = current;

	}

	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		current.setBehavior(this.behavior);
		return true;

	}

}