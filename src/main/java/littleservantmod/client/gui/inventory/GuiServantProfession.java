package littleservantmod.client.gui.inventory;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.packet.MessageOpenGuiId;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiServantProfession extends GuiSideTabContainer {

	/** The location of the inventory background texture */
	public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/littleservant_profession.png");

	public List<LabelIcon> labelIcons;

	public GuiButton option1;
	public GuiButton option2;
	public GuiButton option3;

	public GuiServantProfession(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(servant, playerInventory, inventorySlotsIn);

	}

	@Override
	public void initGui() {
		super.initGui();

		//タブの有効化
		this.professionT.enabled = false;

		//this.buttonList.add(new LabelText(11, this.guiLeft + 38, this.guiTop + 48, servant));

		labelIcons = Lists.newArrayList();

		LabelIcon labelIcon = new LabelIcon(11, this.guiLeft + 9, this.guiTop + 15, servant);
		this.buttonList.add(labelIcon);
		labelIcons.add(labelIcon);

		this.buttonList.add(new LabelText(12, this.guiLeft + 38, this.guiTop + 18, servant));

		LabelIcon labelIcon2 = new LabelIcon(13, this.guiLeft + 9, this.guiTop + 51, servant).setNormal(true);
		this.buttonList.add(labelIcon2);
		labelIcons.add(labelIcon2);

		this.buttonList.add(new LabelText(14, this.guiLeft + 38, this.guiTop + 54, servant).setNormal(true));

		LabelIcon labelIcon3 = new LabelIcon(15, this.guiLeft + 9, this.guiTop + 87, servant).setNormal(true);
		this.buttonList.add(labelIcon3);
		labelIcons.add(labelIcon3);

		this.buttonList.add(new LabelText(16, this.guiLeft + 38, this.guiTop + 90, servant).setNormal(true));

		//
		option1 = new GuiButton(5, this.guiLeft + 143, this.guiTop + 17 + 1, 22, 20, "@");
		this.buttonList.add(option1);

		option2 = new GuiButton(6, this.guiLeft + 143, this.guiTop + 53 + 1, 22, 20, "@");
		option2.enabled = false;
		this.buttonList.add(option2);

		option3 = new GuiButton(7, this.guiLeft + 143, this.guiTop + 89 + 1, 22, 20, "@");
		option3.enabled = false;
		this.buttonList.add(option3);

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

		int buttonID = button.id;

		if (!button.enabled) return;

		if (buttonID == 11 || buttonID == 12) {
			//changeStage(Stage.Profession);

			OpenGuiEntityId id = new OpenGuiEntityId(this.servant);

			LSMPacketHandler.INSTANCE.sendToServer(new MessageOpenGuiId(10, id.getX()));

		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		this.fontRenderer.drawString(I18n.translateToLocal("gui." + "profession" + ".name"), 8, 6, 4210752);

		//this.fontRenderer.drawString(this.servant.getProfession().getProfessionDisplayName(servant), 38, 6 + 18, 4210752);

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
