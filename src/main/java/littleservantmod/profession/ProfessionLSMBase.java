package littleservantmod.profession;

import java.util.Map;

import com.google.common.collect.Maps;

import littleservantmod.LSMProxy;
import littleservantmod.LSMProxy.ClientProxy;
import littleservantmod.LittleServantMod;
import littleservantmod.api.IServant;
import littleservantmod.api.LittleServantModAPI;
import littleservantmod.api.profession.ProfessionBase;
import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.profession.behavior.BehaviorEscort;
import littleservantmod.profession.behavior.BehaviorFree;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ProfessionLSMBase extends ProfessionBase {

    public IMode modeBasic;

    public IBehavior behaviorEscort;
    public IBehavior behaviorFree;

    /** エスコート */
    public static ResourceLocation kyeEscort = new ResourceLocation(LittleServantMod.MOD_ID, "behavior_escort");
    public static IconHolder iconEscort = new IconHolder() {

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon() {
            return ((ClientProxy) (LSMProxy.getProxy())).escort;
        }

    };

    /** 自由行動 */
    public static ResourceLocation kyeFree = new ResourceLocation(LittleServantMod.MOD_ID, "behavior_free");
    public static IconHolder iconFree = new IconHolder() {

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon() {
            return ((ClientProxy) (LSMProxy.getProxy())).free;
        }

    };

    public ProfessionLSMBase() {

        //Mode
        this.modeBasic = LittleServantModAPI.professionManager.getBasicMode();

        //Behavior
        this.behaviorEscort = new BehaviorEscort().setIconHolder(iconEscort).setUnlocalizedName("behavior_escort").setRegistryName(kyeEscort);

        this.behaviorFree = new BehaviorFree().setIconHolder(iconFree).setUnlocalizedName("behavior_free").setRegistryName(kyeFree);

    }

    @Override
    public Map<ResourceLocation, IMode> initModes(IServant servant) {

        Map<ResourceLocation, IMode> map = Maps.newLinkedHashMap();

        map.put(LittleServantModAPI.professionManager.getBasicModeKey(), this.modeBasic);

        return map;

    }

    @Override
    public Map<ResourceLocation, IBehavior> initBehavior(IServant servant) {

        Map<ResourceLocation, IBehavior> map = Maps.newLinkedHashMap();

        map.put(kyeEscort, behaviorEscort);
        map.put(kyeFree, behaviorFree);

        return map;

    }

    @Override
    public IMode getDefaultMode(IServant servant) {
        return this.modeBasic;
    }

    @Override
    public IBehavior getDefaultBehavior(IServant servant) {
        return this.behaviorEscort;
    }

    protected IconHolder iconHolder;

    public IconHolder getIconHolder() {
        return iconHolder;
    }

    public ProfessionLSMBase setIconHolder(IconHolder iconHolder) {
        this.iconHolder = iconHolder;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(IServant servant) {

        if (this.iconHolder == null || this.iconHolder.getIcon() == null) {
            //Missing
            return net.minecraft.client.Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        }

        return this.iconHolder.getIcon();
    }

    @Override
    public boolean isEnableProfession(IServant servant) {
        return true;
    }

    @Override
    public boolean hasOption(IServant servant) {
        return false;
    }

}
