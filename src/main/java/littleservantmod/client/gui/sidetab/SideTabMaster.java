package littleservantmod.client.gui.sidetab;

import littleservantmod.client.gui.inventory.GuiSideTabContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * 契約者の情報を表示する
 * @author shift02
 */
public class SideTabMaster extends SideTabBase {

	GuiSideTabContainer guiSideTabContainer;

	public SideTabMaster(GuiSideTabContainer guiSideTabContainer) {
		super(guiSideTabContainer);
		this.guiSideTabContainer = guiSideTabContainer;

		this.name = "sidetab.master_info.name";

		this.backgroundColor = 0x1E90FF;

	}

	@Override
	public ItemStack getTabIcon() {
		return new ItemStack(Items.CAKE);
	}

	@Override
	public void draw() {

		if (this.open) {

			int i = 1;

			this.elementFontRenderer.drawString(this.guiSideTabContainer.localize(getName()), posX + 24, posY + 8 + i, 0xFFD700, true);

			this.elementFontRenderer.drawString(this.guiSideTabContainer.localize("sidetab.master_name.name"), posX + 24, posY + 21 + i, 0xFFFFFF, true);

			String masterName = "None";
			if (guiSideTabContainer.servant.getOwner() != null) masterName = guiSideTabContainer.servant.getOwner().getDisplayName().getFormattedText();

			this.elementFontRenderer.drawString(
					masterName, posX + 30,
					posY + 33 + i, 0x000000);

		}

	}

}
