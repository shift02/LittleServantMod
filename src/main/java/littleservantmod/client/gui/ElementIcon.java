package littleservantmod.client.gui;

import org.lwjgl.opengl.GL11;

import littleservantmod.client.gui.inventory.GuiServantBase;
import littleservantmod.client.gui.inventory.GuiServantSelect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

public abstract class ElementIcon extends ElementBase {

	protected GuiServantSelect gui;

	public ElementIcon(GuiContainer gui, int posX, int posY) {
		super(gui, posX, posY);

		this.gui = (GuiServantSelect) gui;

	}

	@Override
	public void draw() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		((GuiServantBase) gui).setZLevel(100.0f);
		((GuiServantBase) gui).setItemZLevel(100.0f);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		gui.drawTexturedModalRect(this.posX, this.posY, this.getIcon(), 16, 16);

		((GuiServantBase) gui).setItemZLevel(0.0f);
		((GuiServantBase) gui).setZLevel(0);

		GL11.glDisable(GL11.GL_DEPTH_TEST);

	}

	public abstract String getDisplayName();

	public abstract TextureAtlasSprite getIcon();

}
