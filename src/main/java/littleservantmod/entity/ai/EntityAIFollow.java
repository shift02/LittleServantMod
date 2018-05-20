package littleservantmod.entity.ai;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFollow extends EntityAIBase {
	/** The child that is following its parent. */
	EntityLiving childAnimal;
	Entity parentAnimal;
	Class<? extends Entity> followTargetClass;
	double moveSpeed;
	private int delayCounter;

	public EntityAIFollow(EntityLiving animal, Class<? extends Entity> followTargetClass, double speed) {
		this.childAnimal = animal;
		this.followTargetClass = followTargetClass;
		this.moveSpeed = speed;

		this.setMutexBits(3);

	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {

		List<Entity> list = this.childAnimal.world.<Entity> getEntitiesWithinAABB(
				followTargetClass, this.childAnimal.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
		Entity entityanimal = null;
		double d0 = Double.MAX_VALUE;

		for (Entity entityanimal1 : list) {
			double d1 = this.childAnimal.getDistanceSq(entityanimal1);

			if (d1 <= d0) {
				d0 = d1;
				entityanimal = entityanimal1;
			}

		}

		if (entityanimal == null) {
			return false;
		} else if (d0 < 9.0D) {
			return false;
		} else {
			this.parentAnimal = entityanimal;
			return true;
		}

	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {

		if (!this.parentAnimal.isEntityAlive()) {
			return false;
		} else {
			double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
			return d0 >= 9.0D && d0 <= 256.0D;
		}

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.delayCounter = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.parentAnimal = null;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask() {
		if (--this.delayCounter <= 0) {
			this.delayCounter = 10;
			this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
		}
	}
}