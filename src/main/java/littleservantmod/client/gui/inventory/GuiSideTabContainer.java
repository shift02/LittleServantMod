package littleservantmod.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import littleservantmod.client.gui.sidetab.SideTabBase;
import littleservantmod.client.gui.sidetab.SideTabMaster;
import littleservantmod.client.gui.sidetab.SideTabTracker;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Loader;

public class GuiSideTabContainer extends GuiServantBase {

	protected boolean drawInventory = true;
	protected int mouseX = 0;
	protected int mouseY = 0;

	public List<SideTabBase> tabs = new ArrayList<>();
	protected List<String> tooltip = new LinkedList<String>();

	public EntityLittleServant servant;

	public GuiSideTabContainer(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(inventorySlotsIn, playerInventory);

		this.servant = servant;

	}

	@Override
	public void initGui() {

		super.initGui();
		tabs.clear();

		this.addTab(new SideTabMaster(this));

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		super.drawGuiContainerForegroundLayer(x, y);

		if (!Loader.isModLoaded("NotEnoughItems") && mc.player.inventory.getItemStack() == null) {
			addTooltips(tooltip);
			drawTooltip(tooltip);
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {

		super.drawGuiContainerBackgroundLayer(partialTicks, x, y);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		drawSideTabs();
	}

	public void drawItemStack(ItemStack item, int posX, int posY) {

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.itemRender.zLevel = 100.0F;
		this.itemRender.renderItemAndEffectIntoGUI(item, posX, posY);
		this.itemRender.zLevel = 0.0F;
	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton) throws IOException {

		super.mouseClicked(x, y, mouseButton);

		SideTabBase tab = getTabAtPosition(mouseX, mouseY);

		if (tab != null && !tab.handleMouseClicked(mouseX, mouseY, mouseButton)) {
			for (SideTabBase other : tabs) {
				if (other != tab && other.open) {
					other.toggleOpen();
				}
			}
			tab.toggleOpen();

			//System.out.println("mouseClicked0");

		}
		//System.out.println("mouseClicked : "+mouseX+" : "+mouseY);
		//ElementBase element = getElementAtPosition(mouseX, mouseY);

		//if (element != null) {
		//	element.handleMouseClicked(mouseX, mouseY, mouseButton);
		//}
	}

	@Override
	public void handleMouseInput() throws IOException {

		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		super.handleMouseInput();
	}

	protected void drawSideTabs() {

		int yPosRight = 4;
		int yPosLeft = 4;

		for (SideTabBase tab : tabs) {
			tab.update();
			if (!tab.isVisible()) {
				continue;
			}
			tab.draw(guiLeft + xSize, guiTop + yPosRight);
			yPosRight += tab.currentHeight;

		}
	}

	public void addTooltips(List<String> tooltip) {

		SideTabBase tab = getTabAtPosition(mouseX, mouseY);

		if (tab != null) {
			tab.addTooltip(tooltip);
		}
	}

	public SideTabBase addTab(SideTabBase tab) {

		tabs.add(tab);
		if (SideTabTracker.getOpenedRightTab() != null && tab.getClass().equals(SideTabTracker.getOpenedRightTab())) {
			tab.setFullyOpen();
		}
		return tab;
	}

	protected SideTabBase getTabAtPosition(int mX, int mY) {

		int xShift = xSize;
		int yShift = 4;

		for (SideTabBase tab : tabs) {
			if (!tab.isVisible()) {
				continue;
			}
			tab.currentShiftX = xShift;
			tab.currentShiftY = yShift;
			if (tab.intersectsWith(mX, mY, xShift, yShift)) {
				return tab;
			}
			yShift += tab.currentHeight;
		}

		return null;
	}

	public void drawTooltip(List<String> list) {

		drawTooltipHoveringText(list, mouseX, mouseY, fontRenderer);
		tooltip.clear();
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
		itemRender.zLevel = 0.0F;
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}

	public static String localize(String key) {

		return I18n.translateToLocal(key);
	}

}
