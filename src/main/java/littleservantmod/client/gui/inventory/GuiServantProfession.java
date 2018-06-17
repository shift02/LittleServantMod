package littleservantmod.client.gui.inventory;

import java.io.IOException;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiServantProfession extends GuiSideTabContainer {

	/** The location of the inventory background texture */
	public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/littleservant_profession.png");

	public static final ResourceLocation INVENTORY_PROFESSION_BACKGROUND = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/littleservant_profession_changeprofession.png");

	public Stage stage;

	public GuiServantProfession(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(servant, playerInventory, inventorySlotsIn);

		stage = Stage.Normal;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void initGui() {
		super.initGui();

		this.professionT.enabled = false;

		//this.buttonList.add(new GuiButton(4, this.guiLeft + 120, this.guiTop + 6, "Ok"));

		//this.buttonList.add(new GuiButton(5, this.guiLeft + 6, this.guiTop + 6, "cancel"));

		//this.buttonList.add(new LabelText(11, this.guiLeft + 38, this.guiTop + 48, servant));

		this.buttonList.add(new LabelIcon(10, this.guiLeft + 9, this.guiTop + 15, servant));

		this.buttonList.add(new LabelText(12, this.guiLeft + 38, this.guiTop + 18, servant));

	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 0) {

		}

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		super.actionPerformed(button);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		if (this.stage == Stage.Normal) {

			this.fontRenderer.drawString(I18n.translateToLocal("gui." + "profession" + ".name"), 8, 6, 4210752);

			//this.fontRenderer.drawString(this.servant.getProfession().getProfessionDisplayName(servant), 38, 6 + 18, 4210752);

			this.fontRenderer.drawString(I18n.translateToLocal("gui." + "mode" + ".name"), 8, 6 + 36, 4210752);

			this.fontRenderer.drawString(I18n.translateToLocal("gui." + "sub_mode" + ".name"), 8, 6 + 72, 4210752);

		} else if (this.stage == Stage.Profession) {

		}

		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.stage == Stage.Normal) {
			this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
		} else if (this.stage == Stage.Profession) {
			this.mc.getTextureManager().bindTexture(INVENTORY_PROFESSION_BACKGROUND);
		}

		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if (this.stage == Stage.Normal) {
			//this.drawGuiContainerBackgroundLayerNormal(partialTicks, mouseX, mouseY);
		} else if (this.stage == Stage.Profession) {

		}

	}

	protected void drawGuiContainerBackgroundLayerNormal(float partialTicks, int mouseX, int mouseY) {

		int i = this.guiLeft;
		int j = this.guiTop;

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
		if (this.stage == Stage.Normal) {
			this.drawGuiContainerBackgroundLayerHoveringText(mouseX - i, mouseY - j);
		} else if (this.stage == Stage.Profession) {
		}

	}

	protected void drawGuiContainerBackgroundLayerHoveringText(int mouseX, int mouseY) {

		int i = this.guiLeft;
		int j = this.guiTop;

		if (11 < mouseX && mouseX <= 135 && 17 < mouseY && mouseY <= 39) {
			this.drawHoveringText(I18n.translateToLocal("gui.change_profession"), i + mouseX, j + mouseY);
		}

	}

	enum Stage {

		Normal, Profession;

	}

}
