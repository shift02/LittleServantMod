package littleservantmod.api.profession;

import java.util.Map;

import javax.annotation.Nonnull;

import littleservantmod.api.IServant;
import littleservantmod.api.LittleServantModAPI;
import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * サーヴァントの職業情報を定義するインターフェイス <br />
 * 職業固有のデータを保存したい時は {@link INBTSerializable} を実装する
 * @see INBTSerializable
 * */
public interface IProfession {

	/** AIの初期化時に呼ばれる */
	void initAI(IServant servant);

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon(IServant servant);

	public String getProfessionDisplayName(IServant servant);

	public ResourceLocation getRegistryName();

	public boolean isEnableProfession(IServant servant);

	public boolean hasOption(IServant servant);

	/**
	 * DefaultのModeを返す
	 * Modeが一つしか無いときは {@link LittleServantModAPI#getBasicMode()} を使ってもOK
	 * @see LittleServantModAPI
	 * */
	@Nonnull
	public IMode getDefaultMode(IServant servant);

	public Map<ResourceLocation, IMode> initModes(IServant servant);

	/**
	 * DefaultのBehaviorを返す
	 * */
	@Nonnull
	public IBehavior getDefaultBehavior(IServant servant);

	public Map<ResourceLocation, IBehavior> initBehavior(IServant servant);

}
