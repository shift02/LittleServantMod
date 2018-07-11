package littleservantmod.client.gui;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.IProfession;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ElementChangeProfessionSelect extends ElementChangeProfession {

	protected ElementChangeProfessionCurrent current;

	public ElementChangeProfessionSelect(GuiContainer gui, int posX, int posY, IProfession profession, IServant servant, ElementChangeProfessionCurrent current) {
		super(gui, posX, posY, profession, servant);

		this.current = current;

	}

	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		current.setProfession(this.profession);
		return true;

	}

}
