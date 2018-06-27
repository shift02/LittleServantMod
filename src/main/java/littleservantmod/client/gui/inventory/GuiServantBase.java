package littleservantmod.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;

import littleservantmod.client.gui.ElementBase;
import littleservantmod.client.gui.toptab.TabButton;
import littleservantmod.client.gui.toptab.TabInventory;
import littleservantmod.client.gui.toptab.TabProfession;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
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

	public EntityLittleServant servant;

	protected TabButton inventoryT;
	protected TabButton professionT;

	protected ArrayList<ElementBase> elements = new ArrayList<>();

	public GuiServantBase(EntityLittleServant servant, Container inventorySlotsIn, InventoryPlayer playerInventory) {
		super(inventorySlotsIn);
		this.playerInventory = playerInventory;

		this.servant = servant;

	}

	@Override
	public void initGui() {
		super.initGui();

		this.mc.player.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2 + (14);

		inventoryT = new TabButton(new TabInventory(this.servant));
		inventoryT.x = guiLeft;
		inventoryT.y = this.guiTop - 28;
		inventoryT.id = 2;
		this.buttonList.add(inventoryT);

		int count = 0;
		professionT = new TabButton(new TabProfession(this.servant));
		professionT.x = guiLeft + 32 + count * 29;
		professionT.y = this.guiTop - 28;
		this.buttonList.add(professionT);

		elements.clear();

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.renderHoveredToolTip(mouseX, mouseY);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		updateElements();

		drawElements();

	}

	protected void drawElements() {

		for (ElementBase element : elements) {
			element.draw();
		}
	}

	protected void updateElements() {
		for (ElementBase element : elements) {
			element.update();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		ElementBase element = getElementAtPosition(mouseX, mouseY);

		if (element != null) {
			element.handleMouseClicked(mouseX, mouseY, mouseButton);
		}

	}

	protected ElementBase getElementAtPosition(int mX, int mY) {

		for (ElementBase element : elements) {
			if (element.intersectsWith(mX, mY)) {
				return element;
			}
		}
		return null;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		RenderHelper.disableStandardItemLighting();

		for (GuiButton guibutton : this.buttonList) {
			if (guibutton.isMouseOver()) {
				guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
				break;
			}
		}

		RenderHelper.enableGUIStandardItemLighting();
	}

	public ElementBase addElement(ElementBase element) {

		elements.add(element);
		return element;
	}

	public void setZLevel(float zLevel) {
		this.zLevel = zLevel;
	}

	public void setItemZLevel(float zLevel) {
		this.itemRender.zLevel = zLevel;
	}

}
