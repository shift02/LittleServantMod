package littleservantmod.api.profession;

import net.minecraft.item.ItemStack;

public interface IServantTool {

    public ItemStack getTool();

    public boolean isTool(ItemStack item);

}
