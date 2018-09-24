package littleservantmod.entity.ai.target;

import java.util.function.BiPredicate;

import littleservantmod.entity.EntityLittleServantBlock;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAITargetBlock extends EntityAIBase {

    private final EntityLittleServantBlock servant;

    /** Controls task execution delay */
    protected int runDelay;

    private final int searchLength;
    private final BiPredicate<World, BlockPos> targetSelector;

    private BlockPos target = BlockPos.ORIGIN;

    public EntityAITargetBlock(EntityLittleServantBlock servant, int length, final BiPredicate<World, BlockPos> targetSelector) {

        this.servant = servant;
        this.searchLength = length;
        this.targetSelector = targetSelector;

    }

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

    private boolean searchForDestination() {
        System.out.println("searchForDestination");
        int i = this.searchLength;
        int j = 1;
        BlockPos blockpos = new BlockPos(this.servant);

        for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k) {
            for (int l = 0; l < i; ++l) {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);

                        if (this.servant.isWithinHomeDistanceFromPosition(blockpos1) && this.shouldMoveTo(this.servant.world, blockpos1)) {
                            this.target = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        return this.targetSelector.test(worldIn, pos);
    }

    @Override
    public void startExecuting() {

        //検索したBlockをTargetに追加
        this.servant.setUseTargetBlock(target);

    }

    @Override
    public void resetTask() {
        this.servant.setUseTargetBlock(BlockPos.ORIGIN);
        this.target = null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

}
