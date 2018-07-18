package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServantFakePlayer;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * ホーム外に移動しないようにするAI
 * */
public class EntityAIMoveTowardsRestriction2 extends EntityAIBase {
	private final EntityLittleServantFakePlayer servant;
	private double movePosX;
	private double movePosY;
	private double movePosZ;
	private final double movementSpeed;

	public EntityAIMoveTowardsRestriction2(EntityLittleServantFakePlayer servantIn, double speedIn) {
		this.servant = servantIn;
		this.movementSpeed = speedIn;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (this.servant.isWithinHomeDistanceCurrentPosition()) {
			return false;
		} else {
			BlockPos blockpos = this.servant.getHomePosition();
			Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.servant, 16, 7, new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ()));

			if (vec3d == null) {
				return false;
			} else {
				this.movePosX = vec3d.x;
				this.movePosY = vec3d.y;
				this.movePosZ = vec3d.z;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return !this.servant.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.servant.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
	}
}