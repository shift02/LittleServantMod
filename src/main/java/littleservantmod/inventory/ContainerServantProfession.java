package littleservantmod.inventory;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerServantProfession extends ContainerServantBase {

	public ContainerServantProfession(InventoryPlayer playerInventory, IInventory servantInventoryIn, EntityLittleServant servantIn) {
		super(playerInventory, servantInventoryIn, servantIn);
		// TODO 自動生成されたコンストラクター・スタブ

		//プレイヤーのアイテム欄
		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlotToContainer(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + 42 + l * 18));
			}
		}

		//プレイヤーのアイテム欄その2
		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 142 + 42));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
