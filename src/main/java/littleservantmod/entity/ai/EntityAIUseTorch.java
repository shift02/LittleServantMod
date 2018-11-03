package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.profession.ServantToolManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIUseTorch extends EntityAIBase {

    private final EntityLittleServant servant;

    public EntityAIUseTorch(EntityLittleServant servant) {

        this.servant = servant;

        this.setMutexBits(4);

    }

    @Override
    public boolean shouldExecute() {

        if (servant.world.rand.nextInt(8) != 0) {
            return false;
        }

        return shouldUse(servant.world, servant.getPosition().down());
    }

    @Override
    public void updateTask() {

        BlockPos target = servant.getPosition().down();

        if (this.shouldUse(servant.world, target)) {

            World world = this.servant.world;
            BlockPos blockpos = target.up();
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (iblockstate.getMaterial() == Material.AIR && ServantToolManager.getInstance().isTool(ServantToolManager.torcher, this.servant.getHeldItemMainhand())) {
                ItemStack itemstack = this.servant.getHeldItemMainhand();
                if (itemstack.getItem() instanceof ItemBlock) {

                    ItemBlock itemBlock = (ItemBlock) itemstack.getItem();
                    IBlockState state = Block.getBlockFromItem(itemBlock).getStateForPlacement(world, blockpos, EnumFacing.UP, 0.5f, 1.0f, 0.5f, itemstack.getMetadata(), this.servant, EnumHand.MAIN_HAND);
                    world.setBlockState(blockpos, state, 3);
                    this.servant.swingArm(EnumHand.MAIN_HAND);
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        this.servant.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                    }

                }

            }

        }

    }

    protected boolean shouldUse(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();

        if (worldIn.getLightFromNeighbors(pos.up()) < 8) {
            pos = pos.up();
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (iblockstate.getMaterial() == Material.AIR) {
                return true;
            }
        }

        return false;
    }

}