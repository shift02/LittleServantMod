package littleservantmod.api.profession;

import net.minecraft.item.ItemStack;

public class ServantToolBase implements IServantTool {

    protected final ItemStack tool;

    public ServantToolBase(ItemStack tool) {
        this.tool = tool;
    }

    @Override
    public boolean isTool(ItemStack item) {
        return this.tool.isItemEqualIgnoreDurability(item);
    }

    @Override
    public ItemStack getTool() {
        return tool;
    }

}
