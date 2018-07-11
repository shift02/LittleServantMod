package littleservantmod.client.gui.inventory;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public abstract class GuiServantSelect extends GuiSideTabContainer {

	public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/littleservant_profession_changeprofession.png");

	public GuiServantSelect(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(servant, playerInventory, inventorySlotsIn);
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		this.fontRenderer.drawString(
				I18n.translateToLocal(getGUITitle()) +
						" - " +
						getCurrentTitle(),
				8, 6, 4210752);

		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

	}

	protected abstract String getGUITitle();

	protected abstract String getCurrentTitle();

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);

		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

}
