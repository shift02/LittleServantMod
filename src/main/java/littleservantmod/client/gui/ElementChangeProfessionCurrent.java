package littleservantmod.client.gui;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.IProfession;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ElementChangeProfessionCurrent extends ElementChangeProfession {

	protected IProfession now;

	protected GuiButton buttonOk;

	public ElementChangeProfessionCurrent(GuiContainer gui, int posX, int posY, IProfession profession, IServant servant, GuiButton buttonOk) {
		super(gui, posX, posY, profession, servant);

		this.now = profession;

		this.buttonOk = buttonOk;

	}

	public void setProfession(IProfession profession) {
		this.profession = profession;

		this.buttonOk.enabled = !this.now.equals(this.profession);

	}

	public IProfession getProfession() {
		return this.profession;
	}

}
