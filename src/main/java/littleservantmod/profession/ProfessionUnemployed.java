package littleservantmod.profession;

import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServantBase;
import littleservantmod.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.ResourceLocation;

/** 無職 */
public class ProfessionUnemployed extends ProfessionBase {

	public static ResourceLocation resourceLocation = new ResourceLocation(LittleServantMod.MOD_ID, "unemployed");

	@Override
	public ResourceLocation getID() {
		return resourceLocation;
	}

	@Override
	public void initAI(EntityLittleServantBase servant) {

		super.initAI(servant);

		//うさぎについていく
		servant.tasks.addTask(500, new EntityAIFollow(servant, EntityRabbit.class, 0.5D));

		//うさぎを見る
		servant.tasks.addTask(900, new EntityAIWatchClosest(servant, EntityRabbit.class, 4.0F));

	}

}
