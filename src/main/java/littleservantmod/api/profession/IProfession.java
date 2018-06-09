package littleservantmod.api.profession;

import littleservantmod.api.IServant;
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

}
