package littleservantmod.profession;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import littleservantmod.LittleServantMod;
import littleservantmod.api.profession.IProfessionTool;
import littleservantmod.api.profession.IProfessionToolManager;
import littleservantmod.api.profession.ProfessionToolBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ProfessionToolManager implements IProfessionToolManager {

	public static ProfessionToolManager instance;

	public static ProfessionToolManager getInstance() {
		if (instance == null) instance = new ProfessionToolManager();

		return instance;
	}

	private ListMultimap<ResourceLocation, IProfessionTool> tools = ArrayListMultimap.create();

	public static ResourceLocation saber = new ResourceLocation(LittleServantMod.MOD_ID, "saber");

	public static ResourceLocation archer = new ResourceLocation(LittleServantMod.MOD_ID, "archer");

	public static ResourceLocation ripper = new ResourceLocation(LittleServantMod.MOD_ID, "ripper");

	public static ResourceLocation torcher = new ResourceLocation(LittleServantMod.MOD_ID, "torcher");

	public ProfessionToolManager() {

		//バニラ
		//剣士
		addProfessionTool(saber, new ProfessionToolBase(new ItemStack(Items.WOODEN_SWORD)));
		addProfessionTool(saber, new ProfessionToolBase(new ItemStack(Items.STONE_SWORD)));
		addProfessionTool(saber, new ProfessionToolBase(new ItemStack(Items.IRON_SWORD)));
		addProfessionTool(saber, new ProfessionToolBase(new ItemStack(Items.GOLDEN_SWORD)));
		addProfessionTool(saber, new ProfessionToolBase(new ItemStack(Items.DIAMOND_SWORD)));

		//弓兵
		addProfessionTool(archer, new ProfessionToolBase(new ItemStack(Items.BOW)));

		//毛刈り兵
		addProfessionTool(ripper, new ProfessionToolBase(new ItemStack(Items.SHEARS)));

		//たいまつ兵
		addProfessionTool(torcher, new ProfessionToolBase(new ItemStack(Blocks.TORCH)));

	}

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
