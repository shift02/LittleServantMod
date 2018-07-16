package littleservantmod.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import littleservantmod.client.gui.ElementChangeMode;
import littleservantmod.client.gui.ElementChangeModeCurrent;
import littleservantmod.client.gui.ElementChangeModeSelect;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.packet.MessageChangeMode;
import littleservantmod.packet.MessageOpenGuiId;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.translation.I18n;

/**
 * 職業のモードを設定するGUI
 * */
public class GuiServantSelectMode extends GuiServantSelect {

	private GuiButton buttonCancel;

	private GuiButton buttonOk;

	private ElementChangeModeCurrent current;

	private List<ElementChangeMode> elementChangeIcon = new ArrayList<>();

	public GuiServantSelectMode(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(servant, playerInventory, inventorySlotsIn);

	}

	@Override
	public void initGui() {
		super.initGui();

		//タブの有効化
		this.professionT.enabled = false;

		buttonCancel = new GuiButton(11, this.guiLeft + 78, this.guiTop + 16, 44, 20, I18n.translateToLocal("gui." + "button_cancel" + ".name"));
		this.addButton(buttonCancel);

		buttonOk = new GuiButton(12, this.guiLeft + 125, this.guiTop + 16, 44, 20, I18n.translateToLocal("gui." + "button_ok" + ".name"));
		this.addButton(buttonOk).enabled = false;

		//職業をセット
		current = new ElementChangeModeCurrent(this, 26, 18, this.servant.getMode(), this.servant, buttonOk);
		this.addElement(current);

		int countX = 0;
		int countY = 0;
		for (int i = 0; i < servant.getModes().length; i++) {

			if (!servant.getModes()[i].isEnableMode(servant)) continue;
			ElementChangeModeSelect changeIcon = new ElementChangeModeSelect(this, 8 + 18 * countX, 40 + 18 * countY, servant.getModes()[i], this.servant, current);
			this.addElement(changeIcon);

			countX++;
			if (countX > 9) {
				countX = 0;
				countY++;
			}
		}

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		super.actionPerformed(button);

		int buttonID = button.id;

		if (!button.enabled) return;

		if (buttonID == 12) {
			LSMPacketHandler.INSTANCE.sendToServer(new MessageChangeMode(this.servant, this.current.getMode()));
		}

		if (buttonID == 11 || buttonID == 12) {

			OpenGuiEntityId id = new OpenGuiEntityId(this.servant);

			LSMPacketHandler.INSTANCE.sendToServer(new MessageOpenGuiId(1, id.getX()));

		}

	}

	@Override
	protected String getGUITitle() {
		return "gui." + "change_mode" + ".name";
	}

	@Override
	protected String getCurrentTitle() {
		return this.current.getDisplayName();
	}

}
