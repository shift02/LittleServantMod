package littleservantmod.inventory;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public abstract class ContainerServantBase extends Container {

	protected final EntityLittleServant servant;

	public ContainerServantBase(InventoryPlayer playerInventory, IInventory servantInventoryIn, EntityLittleServant servantIn) {
		//super(playerInventory, localWorld, servantIn);

		servant = servantIn;
	}

}
