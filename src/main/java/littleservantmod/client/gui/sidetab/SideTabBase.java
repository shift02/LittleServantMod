package littleservantmod.client.gui.sidetab;

import org.lwjgl.opengl.GL11;

import littleservantmod.LittleServantMod;
import littleservantmod.client.gui.inventory.GuiSideTabContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class SideTabBase extends ElementBase {

	public static int tabExpandSpeed = 8;

	public boolean open;

	public int backgroundColor = 0xffffff;

	public int currentShiftX = 0;
	public int currentShiftY = 0;

	public int minWidth = 24;
	public int maxWidth = 124;
	public int currentWidth = minWidth;

	public int minHeight = 24;
	public int maxHeight = 62;
	public int currentHeight = minHeight;

	public static final ResourceLocation DEFAULT_TEXTURE_RIGHT = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/tab_right.png");

	public SideTabBase(GuiSideTabContainer gui) {

		super(gui, 0, 0);
		texture = DEFAULT_TEXTURE_RIGHT;
	}

	@Override
	public void update() {

		if (open && currentWidth < maxWidth) {
			currentWidth += tabExpandSpeed;
		} else if (!open && currentWidth > minWidth) {
			currentWidth -= tabExpandSpeed;
		}

		if (currentWidth > maxWidth) {
			currentWidth = maxWidth;
		} else if (currentWidth < minWidth) {
			currentWidth = minWidth;
		}

		if (open && currentHeight < maxHeight) {
			currentHeight += tabExpandSpeed;
		} else if (!open && currentHeight > minHeight) {
			currentHeight -= tabExpandSpeed;
		}

		if (currentHeight > maxHeight) {
			currentHeight = maxHeight;
		} else if (currentHeight < minHeight) {
			currentHeight = minHeight;
		}

		if (open && currentWidth == maxWidth && currentHeight == maxHeight) {
			setFullyOpen();
		}
	}

	public boolean intersectsWith(int mouseX, int mouseY, int shiftX, int shiftY) {

		if (mouseX >= shiftX && mouseX <= shiftX + currentWidth && mouseY >= shiftY && mouseY <= shiftY + currentHeight) {
			return true;
		}
		return false;
	}

	protected void drawBackground() {

		float colorR = (backgroundColor >> 16 & 255) / 255.0F;
		float colorG = (backgroundColor >> 8 & 255) / 255.0F;
		float colorB = (backgroundColor & 255) / 255.0F;

		GL11.glColor4f(colorR, colorG, colorB, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		gui.drawTexturedModalRect(posX, posY, 0, 256 - currentHeight, 4, currentHeight);
		gui.drawTexturedModalRect(posX + 4, posY, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
		gui.drawTexturedModalRect(posX, posY, 0, 0, 4, 4);
		gui.drawTexturedModalRect(posX + 4, posY + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
	}

	protected void drawTabIcon(ItemStack item) {

		int offsetX = 2;

		//gui.draw

		((GuiSideTabContainer) gui).drawItemStack(item, posX + offsetX + 1, posY + 3 + 1);

		//gui.d
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		//gui.drawTexturedModelRectFromIcon(1, 1, icon, 16, 16);
	}

	public boolean isFullyOpened() {

		return currentWidth >= maxWidth;
	}

	public void setFullyOpen() {

		open = true;
		currentWidth = maxWidth;
		currentHeight = maxHeight;
	}

	public void toggleOpen() {

		if (open) {
			open = false;
			SideTabTracker.setOpenedRightTab(null);
		} else {
			open = true;
			SideTabTracker.setOpenedRightTab(this.getClass());

		}
	}

	@Override
	public void draw(int x, int y) {

		this.posX = x;
		this.posY = y;

		this.drawBackground();
		this.drawTabIcon(getTabIcon());

		draw();
	}

	public abstract ItemStack getTabIcon();

}
