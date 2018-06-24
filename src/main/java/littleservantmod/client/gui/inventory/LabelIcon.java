package littleservantmod.client.gui.inventory;

import org.lwjgl.opengl.GL11;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class LabelIcon extends GuiButton {

	ResourceLocation texture = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/labels.png");

	private EntityLittleServant servant;

	private boolean isNormal = false;

	public LabelIcon(int buttonId, int x, int y, EntityLittleServant servant) {
		super(buttonId, x, y, 26, 26, "");

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
		if (this.visible) {

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			GL11.glEnable(GL11.GL_DEPTH_TEST);

			mc.renderEngine.bindTexture(this.texture);

			int yOfset = isNormal ? 26 : 0;

			this.drawTexturedModalRect(this.x, this.y, 0, yOfset, 26, 26);

			this.zLevel = 100.0F;

			//GlStateManager.disableLighting();
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			this.drawTexturedModalRect(this.x + 5, this.y + 5, this.servant.getProfession().getIcon(servant), 16, 16);
			//GlStateManager.enableLighting();

			this.zLevel = 0.0F;

			GL11.glDisable(GL11.GL_DEPTH_TEST);

		}

	}

}
