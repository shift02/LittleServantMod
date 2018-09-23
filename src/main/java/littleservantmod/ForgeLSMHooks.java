package littleservantmod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import littleservantmod.api.event.entity.item.ItemTossEvent;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;

public class ForgeLSMHooks {

    public static int getTotalArmorValue(EntityLittleServant servant) {
        int ret = servant.getTotalArmorValue();

        for (int x = 0; x < servant.getInventory().armorInventory.size(); x++) {
            ItemStack stack = servant.getInventory().armorInventory.get(x);
            if (stack.getItem() instanceof ISpecialArmor) {
                ret += ((ISpecialArmor) stack.getItem()).getArmorDisplay(servant.getPlayer(), stack, x);
            }
        }
        return ret;
    }

    @Nullable
    public static EntityItem onPlayerTossEvent(@Nonnull EntityLittleServant player, @Nonnull ItemStack item, boolean includeName) {
        player.captureDrops = true;
        EntityItem ret = player.dropItem(item, false, includeName);
        player.capturedDrops.clear();
        player.captureDrops = false;

        if (ret == null) {
            return null;
        }

        ItemTossEvent event = new ItemTossEvent(ret, player);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return null;
        }

        if (!player.world.isRemote) {
            player.getEntityWorld().spawnEntity(event.getEntityItem());
        }
        return event.getEntityItem();
    }

}
