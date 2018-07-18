package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServantBase;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner2 extends EntityAIBase {
	private final EntityLittleServantBase servant;
	private EntityLivingBase owner;
	World world;
	private final double followSpeed;
	private final PathNavigate petPathfinder;
	private int timeToRecalcPath;
	float maxDist;
	float minDist;
	private float oldWaterCost;

	public EntityAIFollowOwner2(EntityLittleServantBase servantIn, double followSpeedIn, float minDistIn, float maxDistIn) {
		this.servant = servantIn;
		this.world = servantIn.world;
		this.followSpeed = followSpeedIn;
		this.petPathfinder = servantIn.getNavigator();
		this.minDist = minDistIn;
		this.maxDist = maxDistIn;
		this.setMutexBits(3);

		if (!(servantIn.getNavigator() instanceof PathNavigateGround) && !(servantIn.getNavigator() instanceof PathNavigateFlying)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.servant.getOwner();

		if (entitylivingbase == null) {
			return false;
		} else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).isSpectator()) {
			return false;
		} else if (this.servant.isSitting()) {
			return false;
		} else if (this.servant.getDistanceSq(entitylivingbase) < this.minDist * this.minDist) {
			return false;
		} else {
			this.owner = entitylivingbase;
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return !this.petPathfinder.noPath() && this.servant.getDistanceSq(this.owner) > this.maxDist * this.maxDist && !this.servant.isSitting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.timeToRecalcPath = 0;
		this.oldWaterCost = this.servant.getPathPriority(PathNodeType.WATER);
		this.servant.setPathPriority(PathNodeType.WATER, 0.0F);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.owner = null;
		this.petPathfinder.clearPath();
		this.servant.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask() {
		this.servant.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, this.servant.getVerticalFaceSpeed());

		if (!this.servant.isSitting()) {
			if (--this.timeToRecalcPath <= 0) {
				this.timeToRecalcPath = 10;

				if (!this.petPathfinder.tryMoveToEntityLiving(this.owner, this.followSpeed)) {
					if (!this.servant.getLeashed() && !this.servant.isRiding()) {
						if (this.servant.getDistanceSq(this.owner) >= 144.0D) {
							int i = MathHelper.floor(this.owner.posX) - 2;
							int j = MathHelper.floor(this.owner.posZ) - 2;
							int k = MathHelper.floor(this.owner.getEntityBoundingBox().minY);

							for (int l = 0; l <= 4; ++l) {
								for (int i1 = 0; i1 <= 4; ++i1) {
									if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1)) {
										this.servant.setLocationAndAngles(i + l + 0.5F, k, j + i1 + 0.5F, this.servant.rotationYaw, this.servant.rotationPitch);
										this.petPathfinder.clearPath();
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_) {
		BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		return iblockstate.getBlockFaceShape(this.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.servant) && this.world.isAirBlock(blockpos.up())
				&& this.world.isAirBlock(blockpos.up(2));
	}
}
