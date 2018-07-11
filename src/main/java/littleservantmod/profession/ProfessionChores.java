package littleservantmod.profession;

import java.util.Map;

import com.google.common.collect.Maps;

import littleservantmod.LSMProxy;
import littleservantmod.LittleServantMod;
import littleservantmod.api.IServant;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.profession.mode.ModeEscort;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 雑用
 * @author shift02
 */
public class ProfessionChores extends ProfessionLSMBase {

	/** エスコート */
	public static ResourceLocation kyeEscort = new ResourceLocation(LittleServantMod.MOD_ID, "chores_escort");
	public static IconHolder iconEscort = new IconHolder() {

		@Override
		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getIcon() {
			return LSMProxy.getProxy().escort;
		}

	};

	/** 自由行動 */
	public static ResourceLocation kyeFree = new ResourceLocation(LittleServantMod.MOD_ID, "chores_free");
	public static IconHolder iconFree = new IconHolder() {

		@Override
		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getIcon() {
			return LSMProxy.getProxy().free;
		}

	};

	public IMode modeEscort;
	public IMode modeFree;

	public ProfessionChores() {

		this.modeEscort = new ModeEscort().setIconHolder(iconEscort).setUnlocalizedName("chores_escort").setRegistryName(kyeEscort);

		this.modeFree = new ModeEscort().setIconHolder(iconFree).setUnlocalizedName("chores_free").setRegistryName(kyeFree);

	}

	@Override
	public boolean hasMode(IServant servant) {
		return true;
	}

	@Override
	public Map<ResourceLocation, IMode> initModes(IServant servant) {

		Map<ResourceLocation, IMode> map = Maps.newLinkedHashMap();

		map.put(kyeEscort, modeEscort);
		map.put(kyeFree, modeFree);

		return map;

	}

	@Override
	public IMode getDefaultMode(IServant servant) {
		return modeEscort;
	}

}
