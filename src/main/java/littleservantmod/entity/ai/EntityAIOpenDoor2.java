package littleservantmod.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.util.EnumHand;

public class EntityAIOpenDoor2 extends EntityAIDoorInteract {

	/** If the entity close the door */
	boolean closeDoor;
	/** The temporisation before the entity close the door (in ticks, always 20 = 1 second) */
	int closeDoorTemporisation;

	public EntityAIOpenDoor2(EntityLiving entitylivingIn, boolean shouldClose) {
		super(entitylivingIn);
		this.entity = entitylivingIn;
		this.closeDoor = shouldClose;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return this.closeDoor && this.closeDoorTemporisation > 0 && super.shouldContinueExecuting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.closeDoorTemporisation = 20;
		this.entity.swingArm(EnumHand.MAIN_HAND);
		this.doorBlock.toggleDoor(this.entity.world, this.doorPosition, true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		if (this.closeDoor) {
			this.entity.swingArm(EnumHand.MAIN_HAND);
			this.doorBlock.toggleDoor(this.entity.world, this.doorPosition, false);
		}
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask() {
		--this.closeDoorTemporisation;
		super.updateTask();
	}
}