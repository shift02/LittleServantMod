package littleservantmod.api;

import net.minecraft.item.ItemStack;

/****
 * サーヴァントのHPを回復するItem
 * @author shift02
 */
public interface ISugarItemHandler {

    public boolean canUse(ItemStack item);

    public float useItem(ItemStack item);

}
