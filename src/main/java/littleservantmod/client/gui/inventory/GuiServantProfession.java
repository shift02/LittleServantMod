package littleservantmod.client.gui.inventory;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiServantProfession extends GuiSideTabContainer {

	/** The location of the inventory background texture */
	public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/littleservant_profession.png");

	public GuiServantProfession(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(servant, playerInventory, inventorySlotsIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		this.fontRenderer.drawString(I18n.translateToLocal("gui." + "profession" + ".name"), 8, 6, 4210752);

		this.fontRenderer.drawString(this.servant.getProfession().getProfessionDisplayName(servant), 38, 6 + 18, 4210752);

		this.fontRenderer.drawString(I18n.translateToLocal("gui." + "mode" + ".name"), 8, 6 + 36, 4210752);

		this.fontRenderer.drawString(I18n.translateToLocal("gui." + "sub_mode" + ".name"), 8, 6 + 72, 4210752);

		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		this.zLevel = 100.0F;
		this.itemRender.zLevel = 100.0F;

		GlStateManager.disableLighting();
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		this.drawTexturedModalRect(i + 14, j + 20, this.servant.getProfession().getIcon(servant), 16, 16);
		GlStateManager.enableLighting();

		this.itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;

	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY) {

		super.renderHoveredToolTip(mouseX, mouseY);

		int i = this.guiLeft;
		int j = this.guiTop;

		this.drawGuiContainerBackgroundLayerHoveringText(mouseX - i, mouseY - j);

	}

	protected void drawGuiContainerBackgroundLayerHoveringText(int mouseX, int mouseY) {

		int i = this.guiLeft;
		int j = this.guiTop;

		if (11 < mouseX && mouseX <= 135 && 17 < mouseY && mouseY <= 39) {
			this.drawHoveringText(I18n.translateToLocal("gui.change_profession"), i + mouseX, j + mouseY);
		}

	}

}
