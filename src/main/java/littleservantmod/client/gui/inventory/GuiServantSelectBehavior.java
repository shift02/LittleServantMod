package littleservantmod.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import littleservantmod.client.gui.ElementChangeBehavior;
import littleservantmod.client.gui.ElementChangeBehaviorCurrent;
import littleservantmod.client.gui.ElementChangeBehaviorSelect;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.packet.LSMPacketHandler;
import littleservantmod.packet.MessageChangeBehavior;
import littleservantmod.packet.MessageOpenGuiId;
import littleservantmod.util.OpenGuiEntityId;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.translation.I18n;

public class GuiServantSelectBehavior extends GuiServantSelect {

	private GuiButton buttonCancel;

	private GuiButton buttonOk;

	private ElementChangeBehaviorCurrent current;

	private List<ElementChangeBehavior> elementChangeIcon = new ArrayList<>();

	public GuiServantSelectBehavior(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
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
		current = new ElementChangeBehaviorCurrent(this, 26, 18, this.servant.getBehavior(), this.servant, buttonOk);
		this.addElement(current);

		int countX = 0;
		int countY = 0;
		for (int i = 0; i < servant.getBehaviorList().length; i++) {

			if (!servant.getBehaviorList()[i].isEnableBehavior(servant)) continue;
			ElementChangeBehaviorSelect changeIcon = new ElementChangeBehaviorSelect(this, 8 + 18 * countX, 40 + 18 * countY, servant.getBehaviorList()[i], this.servant, current);
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
			LSMPacketHandler.INSTANCE.sendToServer(new MessageChangeBehavior(this.servant, this.current.getBehavior()));
		}

		if (buttonID == 11 || buttonID == 12) {

			OpenGuiEntityId id = new OpenGuiEntityId(this.servant);

			LSMPacketHandler.INSTANCE.sendToServer(new MessageOpenGuiId(1, id.getX()));

		}

	}

	@Override
	protected String getGUITitle() {
		return "gui." + "change_behavior" + ".name";
	}

	@Override
	protected String getCurrentTitle() {
		return this.current.getDisplayName();
	}

}
