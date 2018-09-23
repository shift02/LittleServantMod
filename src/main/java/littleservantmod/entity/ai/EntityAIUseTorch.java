package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.profession.ProfessionToolManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIUseTorch extends EntityAIMoveToBlock2 {

    private final EntityLittleServant servant;
    private boolean hasTorchItem;
    /** 0 => torch, -1 => none */
    private int currentTask;

    public EntityAIUseTorch(EntityLittleServant servant, double speedIn) {
        super(servant, speedIn, 16);
        this.servant = servant;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {

            this.currentTask = -1;
            this.hasTorchItem = ProfessionToolManager.getInstance().isTool(ProfessionToolManager.torcher, this.servant.getHeldItemMainhand());
        }

        return super.shouldExecute();
    }

    @Override
    public int getDefaultRunDelay() {
        return 30;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return this.currentTask >= 0 && super.shouldContinueExecuting();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void updateTask() {
        super.updateTask();
        this.servant.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.servant.getVerticalFaceSpeed());

        if (this.getIsAboveDestination()) {
            World world = this.servant.world;
            BlockPos blockpos = this.destinationBlock.up();
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (this.currentTask == 0 && iblockstate.getMaterial() == Material.AIR && ProfessionToolManager.getInstance().isTool(ProfessionToolManager.torcher, this.servant.getHeldItemMainhand())) {
                ItemStack itemstack = this.servant.getHeldItemMainhand();
                if (itemstack.getItem() instanceof ItemBlock) {

                    ItemBlock itemBlock = (ItemBlock) itemstack.getItem();
                    IBlockState state = Block.getBlockFromItem(itemBlock).getStateForPlacement(world, blockpos, EnumFacing.UP, 0.5f, 1.0f, 0.5f, itemstack.getMetadata(), this.servant, EnumHand.MAIN_HAND);
                    world.setBlockState(blockpos, state, 3);
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        this.servant.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                    }

                }

            }

            this.currentTask = -1;
            this.runDelay = 10;
        }
    }

    /**
     * Return true to set given position as destination
     */
    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();

        if (worldIn.getLightFromNeighbors(pos.up()) < 8) {
            pos = pos.up();
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (iblockstate.getMaterial() == Material.AIR && this.hasTorchItem) {
                this.currentTask = 0;
                return true;
            }
        }

        return false;
    }
}