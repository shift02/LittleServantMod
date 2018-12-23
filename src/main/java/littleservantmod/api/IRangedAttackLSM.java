package littleservantmod.api;

import net.minecraft.entity.IRangedAttackMob;

public interface IRangedAttackLSM extends IRangedAttackMob {

    /**
     * 攻撃を開始するタメ時間, Entityはこのメソッドで返した数値分を貯めると攻撃モーションに入る
     * @return
     */
    public int canUseCount();

}
