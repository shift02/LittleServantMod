package littleservantmod.client.gui.toptab;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.GuiContainerEvent.DrawForeground;

public class TabButton extends GuiButton {

	ResourceLocation texture = new ResourceLocation("textures/gui/advancements/tabs.png");
	private AbstractTab tab;
	private RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
	//new RenderItem(
	//        Minecraft.getMinecraft().renderEngine, new ModelManager(Minecraft.getMinecraft().getTextureMapBlocks()), Minecraft.getMinecraft().getItemColors());

	public TabButton(AbstractTab abstractTab) {
		super(0, 0, 0, 28, 32, "");
		this.tab = abstractTab;
		//MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			int yTexPos = this.enabled ? 0 : 32;//3 : 32;
			int ySize = this.enabled ? 32 : 32;//25 : 32;
			int xOffset = this.id == 2 ? 0 : (this.id == 7 ? 5 : 1);
			int yPos = this.y + 0;//(this.enabled ? 3 : 0);

			GL11.glEnable(GL11.GL_DEPTH_TEST);

			float z = this.zLevel;
			this.zLevel = 100.0F;
			this.itemRenderer.zLevel = 100.0F;
			mc.renderEngine.bindTexture(this.texture);
			this.drawTexturedModalRect(this.x, yPos, xOffset * 28, yTexPos, 28, ySize);
			this.itemRenderer.zLevel = 0.0F;
			this.zLevel = 0.0F;

			RenderHelper.enableGUIStandardItemLighting();

			this.zLevel = 120.0F;
			this.itemRenderer.zLevel = 120.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			this.itemRenderer.renderItemAndEffectIntoGUI(tab.getItemStack(), x + 6, y + 8);
			this.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, tab.getItemStack(), x + 6, y + 8, null);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			this.itemRenderer.zLevel = 0.0F;
			this.zLevel = 0.0F;
			RenderHelper.disableStandardItemLighting();

			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			//int k = this.getHoverState(this.hovered);

			/*if (this.hovered) {
			    //this.drawCenteredString(mc.fontRenderer, I18n.format(this.tab.getTabName()), mouseX, mouseY, 0xffffff);
			    List name = new ArrayList<String>();
			    name.add(I18n.format(this.tab.getTabName()));
			    this.drawHoveringText(name, mouseX, mouseY, mc.fontRenderer, mc.currentScreen);
			    //((GuiContainer)mc.currentScreen).drawHoveringText(name, mouseX + 20, mouseY + 5, mc.fontRenderer);
			}*/

			drawButtonForegroundLayer(mouseX, mouseY);

			GL11.glDisable(GL11.GL_DEPTH_TEST);

		}
	}

	//@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void drawForeground(DrawForeground e) {
		this.drawButtonForegroundLayer(e.getMouseX(), e.getMouseY());
	}

	@Override
	public void drawButtonForegroundLayer(int mouseX, int mouseY) {

		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

		if (this.hovered) {
			//this.drawCenteredString(mc.fontRenderer, I18n.format(this.tab.getTabName()), mouseX, mouseY, 0xffffff);
			List name = new ArrayList<String>();
			name.add(I18n.translateToLocal(this.tab.getTabName()));
			this.drawHoveringText(name, mouseX, mouseY, Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().currentScreen);

			//((GuiContainer)mc.currentScreen).drawHoveringText(name, mouseX + 20, mouseY + 5, mc.fontRenderer);
		}

	}

	protected void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font, GuiScreen gui) {
		if (!p_146283_1_.isEmpty()) {

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);

			//RenderHelper.disableStandardItemLighting();
			//GL11.glDisable(GL11.GL_LIGHTING);
			//GL11.glDisable(GL11.GL_DEPTH_TEST);

			int k = 0;
			Iterator iterator = p_146283_1_.iterator();

			while (iterator.hasNext()) {
				String s = (String) iterator.next();
				int l = font.getStringWidth(s);

				if (l > k) {
					k = l;
				}
			}

			int j2 = p_146283_2_ + 12;
			int k2 = p_146283_3_ - 12;
			int i1 = 8;

			if (p_146283_1_.size() > 1) {
				i1 += 2 + (p_146283_1_.size() - 1) * 10;
			}

			if (j2 + k > gui.width) {
				j2 -= 28 + k;
			}

			if (k2 + i1 + 6 > this.height + 25) {
				k2 = this.height + 25 - i1 - 6;
			}

			this.zLevel = 300.0F;
			itemRenderer.zLevel = 300.0F;
			int j1 = -267386864;
			this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
			this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
			this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
			this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
			this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
			int k1 = 1347420415;
			int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
			this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
			this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
			this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
			this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			for (int i2 = 0; i2 < p_146283_1_.size(); ++i2) {
				String s1 = (String) p_146283_1_.get(i2);
				font.drawStringWithShadow(s1, j2, k2, -1);

				if (i2 == 0) {
					k2 += 2;
				}

				k2 += 10;
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);

			this.zLevel = 0.0F;
			itemRenderer.zLevel = 0.0F;

			//GL11.glEnable(GL11.GL_LIGHTING);
			//GL11.glEnable(GL11.GL_DEPTH_TEST);
			//RenderHelper.enableStandardItemLighting();

			GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		}
	}

	protected void drawHoveringTextOld(List<String> textLines, int x, int y, FontRenderer font, GuiScreen gui) {
		net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, gui.width, gui.height, -1, font);

	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		boolean inWindow = this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

		if (inWindow) {
			//mc.thePlayer.openContainer = null;
			this.tab.onTabClicked();
		}

		return inWindow;
	}

	public boolean shouldAddToList() {
		return true;
	}

}