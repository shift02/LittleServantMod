package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServantBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 特定のブロックに移動するAI
 * */
public abstract class EntityAIMoveToBlock2 extends EntityAIBase {
    private final EntityLittleServantBase servant;
    private final double movementSpeed;
    /** Controls task execution delay */
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;
    private final int searchLength;

    public EntityAIMoveToBlock2(EntityLittleServantBase servant, double speedIn, int length) {
        this.servant = servant;
        this.movementSpeed = speedIn;
        this.searchLength = length;
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
            this.runDelay = getDefaultRunDelay() + this.servant.getRNG().nextInt(200);
            return this.searchForDestination();
        }
    }

    public int getDefaultRunDelay() {
        return 200;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.servant.world, this.destinationBlock);
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
        int i = this.searchLength;
        int j = 1;
        BlockPos blockpos = new BlockPos(this.servant);

        for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k) {
            for (int l = 0; l < i; ++l) {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);

                        if (this.servant.isWithinHomeDistanceFromPosition(blockpos1) && this.shouldMoveTo(this.servant.world, blockpos1)) {
                            this.destinationBlock = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Return true to set given position as destination
     */
    protected abstract boolean shouldMoveTo(World worldIn, BlockPos pos);
}
