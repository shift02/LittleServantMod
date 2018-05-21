package littleservantmod.api.profession;

import littleservantmod.entity.EntityLittleServantBase;

public interface IProfessionFactory {

	/**
	 * Entity生成時に呼ばれる
	 * @param servant 職業を追加するサーヴァントのインスタンス
	 * @return 実際に追加するIProfession
	 */
	public IProfession createProfessionFor(EntityLittleServantBase servant);

}
