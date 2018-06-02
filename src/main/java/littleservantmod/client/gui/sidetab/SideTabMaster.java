package littleservantmod.client.gui.sidetab;

import littleservantmod.client.gui.inventory.GuiSideTabContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SideTabMaster extends SideTabBase {

	GuiSideTabContainer guiSideTabContainer;

	public SideTabMaster(GuiSideTabContainer guiSideTabContainer) {
		super(guiSideTabContainer);
		this.guiSideTabContainer = guiSideTabContainer;

		this.name = "master.name";

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

			this.elementFontRenderer.drawString(this.guiSideTabContainer.localize(getName()), posX + 22, posY + 8 + i, 0xFFD700, true);

			this.elementFontRenderer.drawString(this.guiSideTabContainer.localize("master_name.name"), posX + 22, posY + 21 + i, 0xFFFFFF, true);
			this.elementFontRenderer.drawString(
					"Foo", posX + 28,
					posY + 33 + i, 0x000000);

		}

	}

}
