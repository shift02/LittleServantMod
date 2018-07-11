package littleservantmod.client.gui;

import java.util.List;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.IProfession;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ElementChangeProfession extends ElementIcon {

	protected IProfession profession;

	protected IServant servant;

	public ElementChangeProfession(GuiContainer gui, int posX, int posY, IProfession profession, IServant servant) {
		super(gui, posX, posY);
		this.sizeX = 16;
		this.sizeY = 16;

		this.profession = profession;

		this.servant = servant;

	}

	@Override
	public void addTooltip(List<String> list) {
		list.add("sssss");
	}

	@Override
	public String getDisplayName() {
		return profession.getProfessionDisplayName(servant);
	}

	@Override
	public TextureAtlasSprite getIcon() {
		return this.profession.getIcon(servant);
	}

}
