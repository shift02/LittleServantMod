package littleservantmod.entity;

import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityLittleServantBlock extends EntityLittleServantBase {

	/** (Usually one-shot) tasks used to select an use block target */
	public final EntityAITasks targetBlockTasks;

	/** The active target the Task system uses for tracking */
	private BlockPos useTargetBlock = BlockPos.ORIGIN;

	public EntityLittleServantBlock(World worldIn) {
		super(worldIn);
		this.targetBlockTasks = new EntityAITasks(worldIn != null && worldIn.profiler != null ? worldIn.profiler : null);

	}

	@Override
	protected void updateAITasks() {

		super.updateAITasks();

		this.world.profiler.startSection("targetBlockSelector");
		this.targetBlockTasks.onUpdateTasks();
		this.world.profiler.endSection();

	}

	public BlockPos getUseTargetBlock() {
		return useTargetBlock;
	}

	public void setUseTargetBlock(BlockPos useTargetBlock) {
		this.useTargetBlock = useTargetBlock;
	}

}
