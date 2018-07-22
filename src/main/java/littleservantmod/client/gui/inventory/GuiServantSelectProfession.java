package littleservantmod.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import littleservantmod.client.gui.ElementChangeProfession;
import littleservantmod.client.gui.ElementChangeProfessionCurrent;
import littleservantmod.client.gui.ElementChangeProfessionSelect;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.packet.MessageChangeProfession;
import littleservantmod.packet.MessageOpenGuiId;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.translation.I18n;

/**
 * 職業を設定するGUI
 * */
public class GuiServantSelectProfession extends GuiServantSelect {

	private GuiButton buttonCancel;

	private GuiButton buttonOk;

	private ElementChangeProfessionCurrent current;

	private List<ElementChangeProfession> elementChangeIcon = new ArrayList<>();

	public GuiServantSelectProfession(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
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
		current = new ElementChangeProfessionCurrent(this, 26, 18, this.servant.getProfession(), this.servant, buttonOk);
		this.addElement(current);

		int countX = 0;
		int countY = 0;
		for (int i = 0; i < servant.getProfessions().length; i++) {

			if (!servant.getProfessions()[i].isEnableProfession(servant)) continue;
			ElementChangeProfessionSelect changeIcon = new ElementChangeProfessionSelect(this, 8 + 18 * countX, 40 + 18 * countY, servant.getProfessions()[i], this.servant, current);
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
			LSMPacketHandler.INSTANCE.sendToServer(new MessageChangeProfession(this.servant, this.current.getProfession()));
		}

		if (buttonID == 11 || buttonID == 12) {

			OpenGuiEntityId id = new OpenGuiEntityId(this.servant);

			LSMPacketHandler.INSTANCE.sendToServer(new MessageOpenGuiId(1, id.getX()));

		}

	}

	@Override
	protected String getGUITitle() {
		return "gui." + "change_profession" + ".name";
	}

	@Override
	protected String getCurrentTitle() {
		return this.current.getDisplayName();
	}
}
