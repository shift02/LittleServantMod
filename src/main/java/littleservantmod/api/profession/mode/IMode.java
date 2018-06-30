package littleservantmod.api.profession.mode;

import littleservantmod.api.IServant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * サーヴァントの職業内のモードを定義するインターフェイス <br />
 * モード固有のデータを保存したい時は {@link INBTSerializable} を実装する
 * @see INBTSerializable
 * */
public interface IMode {

	/** AIの初期化時に呼ばれる */
	void initAI(IServant servant);

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon(IServant servant);

	public String getModeDisplayName(IServant servant);

	public ResourceLocation getRegistryName();

	public boolean isEnableMode(IServant servant);

}
