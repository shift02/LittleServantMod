package littleservantmod.entity.ai;

import javax.annotation.Nullable;

import littleservantmod.entity.EntityLittleServantBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

public class EntityAIWander extends EntityAIBase {
	protected final EntityLiving entity;
	protected double x;
	protected double y;
	protected double z;
	protected final double speed;
	protected int executionChance;
	protected boolean mustUpdate;

	public EntityAIWander(EntityLiving creatureIn, double speedIn) {
		this(creatureIn, speedIn, 120);
	}

	public EntityAIWander(EntityLiving creatureIn, double speedIn, int chance) {
		this.entity = creatureIn;
		this.speed = speedIn;
		this.executionChance = chance;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {

		if (!this.mustUpdate) {
			if (this.entity.getIdleTime() >= 100) {
				return false;
			}

			if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
				return false;
			}
		}

		Vec3d vec3d = this.getPosition();

		if (vec3d == null) {
			return false;
		} else {
			this.x = vec3d.x;
			this.y = vec3d.y;
			this.z = vec3d.z;
			this.mustUpdate = false;
			return true;
		}
	}

	@Nullable
	protected Vec3d getPosition() {
		return RandomPositionGenerator.findRandomTarget((EntityLittleServantBase) this.entity, 10, 7);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return !this.entity.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
	}

	/**
	 * Makes task to bypass chance
	 */
	public void makeUpdate() {
		this.mustUpdate = true;
	}

	/**
	 * Changes task random possibility for execution
	 */
	public void setExecutionChance(int newchance) {
		this.executionChance = newchance;
	}
}