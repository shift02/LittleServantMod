package littleservantmod.client.gui.inventory;

import org.lwjgl.opengl.GL11;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class LabelText extends GuiButton {

	ResourceLocation texture = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/labels.png");

	EntityLittleServant servant;

	public LabelText(int buttonId, int x, int y, EntityLittleServant servant) {
		super(buttonId, x, y, 97, 20, "");

		this.servant = servant;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {

			if (this.visible) {

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				GL11.glEnable(GL11.GL_DEPTH_TEST);

				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

				mc.renderEngine.bindTexture(this.texture);

				this.drawTexturedModalRect(this.x, this.y, 29, 3, this.width, this.height);
				int j = 14737632;
				this.drawString(mc.fontRenderer, this.servant.getProfession().getProfessionDisplayName(servant), this.x + 6, this.y + 7, j);

				GL11.glDisable(GL11.GL_DEPTH_TEST);

			}

			/*
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(texture);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState(this.hovered);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			//GlStateManager.enableBlend();
			//GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			//GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.drawTexturedModalRect(this.x, this.y, 29, 3, this.width, this.height);
			//this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
			this.mouseDragged(mc, mouseX, mouseY);
			int j = 14737632;
			
			/*
			if (packedFGColour != 0) {
				j = packedFGColour;
			} else if (!this.enabled) {
				j = 10526880;
			} else if (this.hovered) {
				j = 16777120;
			}*/

			/*
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			String displayString2 = this.servant.getProfession().getProfessionDisplayName(servant);
			
			//this.drawCenteredString(fontrenderer, displayString2, this.x + 6, this.y + 10, j);
			
			this.drawString(fontrenderer, displayString2, this.x + 6, this.y + 10, j);
			*/
		}
	}

}
