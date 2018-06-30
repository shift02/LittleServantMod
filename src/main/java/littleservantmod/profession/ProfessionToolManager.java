package littleservantmod.profession;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import littleservantmod.api.profession.IProfessionTool;
import littleservantmod.api.profession.IProfessionToolManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ProfessionToolManager implements IProfessionToolManager {

	private ListMultimap<ResourceLocation, IProfessionTool> tools = ArrayListMultimap.create();

	@Override
	public void addProfessionTool(ResourceLocation type, IProfessionTool tool) {

		tools.put(type, tool);

	}

	public List<IProfessionTool> getIProfessionTools(ResourceLocation type) {
		return this.tools.get(type);
	}

	public boolean isTool(ResourceLocation type, ItemStack item) {

		for (IProfessionTool tool : this.getIProfessionTools(type)) {
			if (tool.isTool(item)) return true;
		}

		return false;

	}

}
