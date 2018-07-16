package littleservantmod.client.gui.inventory;

import org.lwjgl.opengl.GL11;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class LabelIcon extends GuiLSMButton {

	ResourceLocation texture = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/labels.png");

	protected EntityLittleServant servant;

	private boolean isNormal = false;

	public LabelIcon(GuiServantBase gui, int buttonId, int x, int y, EntityLittleServant servant) {
		super(gui, buttonId, x, y, 26, 26, "");

		this.servant = servant;

	}

	public boolean isNormal() {
		return isNormal;
	}

	public LabelIcon setNormal(boolean isNormal) {
		this.isNormal = isNormal;
		return this;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

		preDrawButton();

		if (this.visible) {

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			GL11.glEnable(GL11.GL_DEPTH_TEST);

			mc.renderEngine.bindTexture(this.texture);

			int yOfset = isNormal ? 26 : 0;

			if (!this.enabled) yOfset += 26;

			this.drawTexturedModalRect(this.x, this.y, 0, yOfset, 26, 26);

			this.zLevel = 100.0F;

			//GlStateManager.disableLighting();
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			this.drawTexturedModalRect(this.x + 5, this.y + 5, getIcon(), 16, 16);
			//GlStateManager.enableLighting();

			this.zLevel = 0.0F;

			GL11.glDisable(GL11.GL_DEPTH_TEST);

		}

	}

	protected void preDrawButton() {

	}

	protected TextureAtlasSprite getIcon() {
		return this.servant.getProfession().getIcon(servant);
	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY) {
		gui.drawHoveringText(I18n.translateToLocal(getHoveredToolTip()), mouseX, mouseY);
	}

	protected String getHoveredToolTip() {
		return "tooltip.change_profession.name";
	}

}
