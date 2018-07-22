package littleservantmod.client.gui.sidetab;

import littleservantmod.client.gui.inventory.GuiSideTabContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

/**
 * 現在の職業を表示する
 * @author shift02
 */
public class SideTabProfession extends SideTabBase {

	GuiSideTabContainer guiSideTabContainer;

	public SideTabProfession(GuiSideTabContainer guiSideTabContainer) {

		super(guiSideTabContainer);
		this.guiSideTabContainer = guiSideTabContainer;

		this.name = "sidetab.profession_info.name";

		this.backgroundColor = 0x088A08;

	}

	@Override
	public ItemStack getTabIconFromItem() {
		return null;
	}

	@Override
	public TextureAtlasSprite getTabIcon() {
		return guiSideTabContainer.servant.getProfession().getIcon(guiSideTabContainer.servant);
	}

	@Override
	public void draw() {

		if (this.open) {

			int i = 1;

			this.elementFontRenderer.drawString(this.guiSideTabContainer.localize(getName()), posX + 24, posY + 8 + i, 0xFFD700, true);

			this.elementFontRenderer.drawString(this.guiSideTabContainer.localize("sidetab.profession_name.name"), posX + 24, posY + 21 + i, 0xFFFFFF, true);

			String professionName = guiSideTabContainer.servant.getProfession().getProfessionDisplayName(guiSideTabContainer.servant);

			this.elementFontRenderer.drawString(professionName,
					posX + 30,
					posY + 33 + i,
					0x000000);

		}

	}

}
