package littleservantmod.api.profession;

import littleservantmod.api.IServant;
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
public abstract class ProfessionBase implements IProfession {

	private ResourceLocation resourceLocation;

	public void init() {

	}

	@Override
	public void initAI(IServant servant) {

		this.initDefaultAI(servant);

	}

	protected void initDefaultAI(IServant servant) {

		//砂糖についてくる
		servant.addAI(300, new EntityAITempt(servant.getEntityInstance(), 0.5D, Items.SUGAR, false));

		//ウロウロ
		servant.addAI(800, new EntityAIWanderAvoidWater(servant.getEntityInstance(), 0.5D));

		//プレイヤーを見る
		servant.addAI(900, new EntityAIWatchClosest(servant.getEntityInstance(), EntityPlayer.class, 8.0F));
		// 見回すAIの追加
		servant.addAI(900, new EntityAILookIdle(servant.getEntityInstance()));

	}

	@Override
	public ResourceLocation getRegistryName() {
		return this.resourceLocation;
	}

	public ProfessionBase setRegistryName(ResourceLocation resourceLocation) {
		this.resourceLocation = resourceLocation;
		return this;
	}

}
