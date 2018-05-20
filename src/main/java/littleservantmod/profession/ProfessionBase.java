package littleservantmod.profession;

import littleservantmod.entity.EntityLittleServantBase;
import littleservantmod.entity.ai.EntityAITempt;
import littleservantmod.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

/**
 * サーヴァントの職業クラス
 * @author shift02
 */
public abstract class ProfessionBase {

	public abstract ResourceLocation getID();

	public void init() {

	}

	public void initAI(EntityLittleServantBase servant) {

		this.initDefaultAI(servant);

	}

	protected void initDefaultAI(EntityLittleServantBase servant) {

		//砂糖についてくる
		servant.tasks.addTask(300, new EntityAITempt(servant, 0.5D, Items.SUGAR, false));

		//ウロウロ
		servant.tasks.addTask(800, new EntityAIWanderAvoidWater(servant, 0.5D));

		//プレイヤーを見る
		servant.tasks.addTask(900, new EntityAIWatchClosest(servant, EntityPlayer.class, 8.0F));
		// 見回すAIの追加
		servant.tasks.addTask(900, new EntityAILookIdle(servant));

	}

}
