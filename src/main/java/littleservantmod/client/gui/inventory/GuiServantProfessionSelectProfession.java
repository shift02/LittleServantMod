package littleservantmod.client.gui.inventory;

import java.io.IOException;

import littleservantmod.LittleServantMod;
import littleservantmod.client.gui.GuiSmallButton;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.packet.MessageOpenGuiId;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

/**
 * 名前がくどい
 * 職業を設定するGUI
 * */
public class GuiServantProfessionSelectProfession extends GuiSideTabContainer {

	public static final ResourceLocation INVENTORY_PROFESSION_BACKGROUND = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/littleservant_profession_changeprofession.png");

	private GuiButton buttonCancel;

	private GuiButton buttonOk;

	public GuiServantProfessionSelectProfession(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(servant, playerInventory, inventorySlotsIn);

	}

	@Override
	public void initGui() {
		super.initGui();

		//タブの有効化
		this.professionT.enabled = false;

		buttonCancel = new GuiSmallButton(11, this.guiLeft + 78, this.guiTop + 4, 44, 16, I18n.translateToLocal("gui." + "button_cancel" + ".name"));
		this.addButton(buttonCancel);

		buttonOk = new GuiSmallButton(12, this.guiLeft + 125, this.guiTop + 4, 44, 16, I18n.translateToLocal("gui." + "button_ok" + ".name"));
		this.addButton(buttonOk);

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		super.actionPerformed(button);

		int buttonID = button.id;

		if (!button.enabled) return;

		if (buttonID == 11 || buttonID == 12) {

			OpenGuiEntityId id = new OpenGuiEntityId(this.servant);

			LSMPacketHandler.INSTANCE.sendToServer(new MessageOpenGuiId(1, id.getX()));

		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		this.fontRenderer.drawString(I18n.translateToLocal("gui." + "change_profession" + ".name"), 8, 6, 4210752);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(INVENTORY_PROFESSION_BACKGROUND);

		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

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

}
