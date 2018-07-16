package littleservantmod.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import littleservantmod.client.gui.ElementBase;
import littleservantmod.client.gui.toptab.TabButton;
import littleservantmod.client.gui.toptab.TabInventory;
import littleservantmod.client.gui.toptab.TabProfession;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.gui.FontRenderer;
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

	protected int mouseX = 0;
	protected int mouseY = 0;

	protected List<String> tooltip = new LinkedList<String>();

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

		addTooltips(tooltip);
		drawTooltip(tooltip);

		RenderHelper.enableGUIStandardItemLighting();
	}

	public void addTooltips(List<String> tooltip) {

		ElementBase element = getElementAtPosition(mouseX, mouseY);

		if (element != null) {
			element.addTooltip(tooltip);
		}
	}

	public void drawTooltip(List<String> list) {

		drawTooltipHoveringText(list, mouseX, mouseY, fontRenderer);
		tooltip.clear();
	}

	@Override
	public void handleMouseInput() throws IOException {

		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		super.handleMouseInput();
	}

	protected void drawTooltipHoveringText(List list, int x, int y, FontRenderer font) {

		if (list == null || list.isEmpty()) {
			return;
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		int k = 0;
		Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			String s = (String) iterator.next();
			int l = font.getStringWidth(s);

			if (l > k) {
				k = l;
			}
		}
		int i1 = x + 12;
		int j1 = y - 12;
		int k1 = 8;

		if (list.size() > 1) {
			k1 += 2 + (list.size() - 1) * 10;
		}
		if (i1 + k > this.width) {
			i1 -= 28 + k;
		}
		if (j1 + k1 + 6 > this.height) {
			j1 = this.height - k1 - 6;
		}
		this.zLevel = 300.0F;
		this.itemRender.zLevel = 300.0F;
		int l1 = -267386864;
		this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
		this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
		this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
		this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
		this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
		int i2 = 1347420415;
		int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

		for (int k2 = 0; k2 < list.size(); ++k2) {
			String s1 = (String) list.get(k2);
			font.drawStringWithShadow(s1, i1, j1, -1);

			if (k2 == 0) {
				j1 += 2;
			}
			j1 += 10;
		}
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY) {
		super.renderHoveredToolTip(mouseX, mouseY);

		//button
		for (GuiButton button : this.buttonList) {
			if (button instanceof GuiLSMButton && button.mousePressed(mc, mouseX, mouseY)) {
				((GuiLSMButton) button).renderHoveredToolTip(mouseX, mouseY);
			}
		}

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
