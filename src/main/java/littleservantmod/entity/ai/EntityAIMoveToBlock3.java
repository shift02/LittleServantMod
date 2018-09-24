package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServantBlock;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIMoveToBlock3 extends EntityAIBase {
    private final EntityLittleServantBlock servant;
    private final double movementSpeed;
    /** Controls task execution delay */
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;

    public EntityAIMoveToBlock3(EntityLittleServantBlock servant, double speedIn) {
        this.servant = servant;
        this.movementSpeed = speedIn;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.runDelay > 0) {
            --this.runDelay;
            return false;
        } else {
            this.runDelay = 200 + this.servant.getRNG().nextInt(200);
            return this.searchForDestination();
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.servant.getUseTargetBlock() != BlockPos.ORIGIN;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.servant.getNavigator().tryMoveToXYZ((this.destinationBlock.getX()) + 0.5D, this.destinationBlock.getY() + 1, (this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
        this.timeoutCounter = 0;
        this.maxStayTicks = this.servant.getRNG().nextInt(this.servant.getRNG().nextInt(1200) + 1200) + 1200;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void updateTask() {
        if (this.servant.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0D) {
            this.isAboveDestination = false;
            ++this.timeoutCounter;

            if (this.timeoutCounter % 40 == 0) {
                System.out.println("updateTask");
                this.servant.getNavigator().tryMoveToXYZ((this.destinationBlock.getX()) + 0.5D, this.destinationBlock.getY() + 1, (this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
            }
        } else {
            this.isAboveDestination = true;
            --this.timeoutCounter;
        }

    }

    protected boolean getIsAboveDestination() {
        return this.isAboveDestination;
    }

    /**
     * Searches and sets new destination block and returns true if a suitable block (specified in {@link
     * net.minecraft.entity.ai.EntityAIMoveToBlock#shouldMoveTo(World, BlockPos) EntityAIMoveToBlock#shouldMoveTo(World,
     * BlockPos)}) can be found.
     */
    private boolean searchForDestination() {

        if (this.servant.isWithinHomeDistanceFromPosition(this.servant.getUseTargetBlock()) && this.servant.getUseTargetBlock() != BlockPos.ORIGIN) {
            this.destinationBlock = this.servant.getUseTargetBlock();
            return true;
        }

        return false;
    }

}
