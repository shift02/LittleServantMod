package littleservantmod.profession;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import littleservantmod.LittleServantMod;
import littleservantmod.api.profession.IServantTool;
import littleservantmod.api.profession.IServantToolManager;
import littleservantmod.api.profession.ServantToolBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 各種サーヴァントが扱う道具をジャンル分けするクラス
 * */
public class ServantToolManager implements IServantToolManager {

    public static ServantToolManager instance;

    public static ServantToolManager getInstance() {
        if (instance == null) instance = new ServantToolManager();

        return instance;
    }

    private ListMultimap<ResourceLocation, IServantTool> tools = ArrayListMultimap.create();

    public static ResourceLocation saber = new ResourceLocation(LittleServantMod.MOD_ID, "saber");

    public static ResourceLocation archer = new ResourceLocation(LittleServantMod.MOD_ID, "archer");

    public static ResourceLocation ripper = new ResourceLocation(LittleServantMod.MOD_ID, "ripper");

    public static ResourceLocation torcher = new ResourceLocation(LittleServantMod.MOD_ID, "torcher");

    public ServantToolManager() {

        //TODO Entity個別のデータにしたい

        //バニラ
        //剣士
        addServantTool(saber, new ServantToolBase(new ItemStack(Items.WOODEN_SWORD)));
        addServantTool(saber, new ServantToolBase(new ItemStack(Items.STONE_SWORD)));
        addServantTool(saber, new ServantToolBase(new ItemStack(Items.IRON_SWORD)));
        addServantTool(saber, new ServantToolBase(new ItemStack(Items.GOLDEN_SWORD)));
        addServantTool(saber, new ServantToolBase(new ItemStack(Items.DIAMOND_SWORD)));

        //弓兵
        addServantTool(archer, new ServantToolBase(new ItemStack(Items.BOW)));

        //毛刈り兵
        addServantTool(ripper, new ServantToolBase(new ItemStack(Items.SHEARS)));

        //たいまつ兵
        addServantTool(torcher, new ServantToolBase(new ItemStack(Blocks.TORCH)));

    }

    @Override
    public void addServantTool(ResourceLocation type, IServantTool tool) {

        tools.put(type, tool);

    }

    public List<IServantTool> getIProfessionTools(ResourceLocation type) {
        return this.tools.get(type);
    }

    public boolean isTool(ResourceLocation type, ItemStack item) {

        for (IServantTool tool : this.getIProfessionTools(type)) {
            if (tool.isTool(item)) return true;
        }

        return false;

    }

}
