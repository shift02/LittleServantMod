package littleservantmod.entity.ai;

import javax.annotation.Nullable;

import littleservantmod.entity.EntityLittleServantBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

public class EntityAIWanderAvoidWater extends EntityAIWander {
	protected final float probability;

	public EntityAIWanderAvoidWater(EntityLiving entityLiving, double p_i47301_2_) {
		this(entityLiving, p_i47301_2_, 0.001F);
	}

	public EntityAIWanderAvoidWater(EntityLiving p_i47302_1_, double p_i47302_2_, float p_i47302_4_) {
		super(p_i47302_1_, p_i47302_2_);
		this.probability = p_i47302_4_;
	}

	@Override
	@Nullable
	protected Vec3d getPosition() {
		if (this.entity.isInWater()) {
			Vec3d vec3d = RandomPositionGenerator.getLandPos((EntityLittleServantBase) this.entity, 15, 7);
			return vec3d == null ? super.getPosition() : vec3d;
		} else {
			return this.entity.getRNG().nextFloat() >= this.probability
					? RandomPositionGenerator.getLandPos((EntityLittleServantBase) this.entity, 10, 7)
					: super.getPosition();
		}
	}
}