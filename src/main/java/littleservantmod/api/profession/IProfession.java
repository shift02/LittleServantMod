package littleservantmod.api.profession;

import littleservantmod.api.IServant;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * サーヴァントの職業情報を定義するインターフェイス <br />
 * 職業固有のデータを保存したい時は {@link INBTSerializable} を実装する
 * @see INBTSerializable
 * */
public interface IProfession {

	/** AIの初期化時に呼ばれる */
	void initAI(IServant servant);

	public ResourceLocation getRegistryName();

}
