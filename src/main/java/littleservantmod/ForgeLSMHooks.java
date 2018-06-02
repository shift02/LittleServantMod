package littleservantmod;

import littleservantmod.entity.EntityLittleServant;

public class ForgeLSMHooks {

	public static int getTotalArmorValue(EntityLittleServant servant) {
		int ret = servant.getTotalArmorValue();
		//TODO
		/*
		for (int x = 0; x < player.inventory.armorInventory.size(); x++) {
			ItemStack stack = player.inventory.armorInventory.get(x);
			if (stack.getItem() instanceof ISpecialArmor) {
				ret += ((ISpecialArmor) stack.getItem()).getArmorDisplay(player, stack, x);
			}
		}*/
		return ret;
	}

}
