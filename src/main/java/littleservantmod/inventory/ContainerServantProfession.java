package littleservantmod.inventory;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerServantProfession extends ContainerServantBase {

	public ContainerServantProfession(InventoryPlayer playerInventory, IInventory servantInventoryIn, EntityLittleServant servantIn) {
		super(playerInventory, servantInventoryIn, servantIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
