package littleservantmod.client.gui.inventory;

import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/**
 * GUIのベースクラス
 * @author shift02
 */
public class GuiServantBase extends InventoryEffectRenderer {

	/** The X size of the inventory window in pixels. */
	protected int xSize = 176;
	/** The Y size of the inventory window in pixels. */
	protected int ySize = 208;//166;//208;//166;

	protected final InventoryPlayer playerInventory;

	public GuiServantBase(Container inventorySlotsIn, InventoryPlayer playerInventory) {
		super(inventorySlotsIn);
		this.playerInventory = playerInventory;
	}

	@Override
	public void initGui() {
		super.initGui();

		this.mc.player.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2 + (12);

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.renderHoveredToolTip(mouseX, mouseY);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

	}

}
