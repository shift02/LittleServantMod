package littleservantmod.api.profession;

import net.minecraft.item.ItemStack;

public class ProfessionToolBase implements IProfessionTool {

	protected ItemStack tool;

	public ProfessionToolBase(ItemStack tool) {
		this.tool = tool;
	}

	@Override
	public boolean isTool(ItemStack item) {
		return this.tool.isItemEqualIgnoreDurability(item);
	}

}
