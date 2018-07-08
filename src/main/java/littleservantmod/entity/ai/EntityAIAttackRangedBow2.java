package littleservantmod.entity.ai;

import littleservantmod.profession.ProfessionToolManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

/**
 * 離れたところから攻撃するAI
 * @author shift02
 *
 */
public class EntityAIAttackRangedBow2<T extends EntityLiving, R extends IRangedAttackMob> extends EntityAIBase {

	private final T entity;
	private final R rangedAttack;
	private ResourceLocation toolType;
	private final double moveSpeedAmp;
	private int attackCooldown;
	private final float maxAttackDistance;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;

	public EntityAIAttackRangedBow2(T entity, R rangedAttack, ResourceLocation type, double moveSpeedAmp, int attackCooldown, float p_i47515_5_) {
		this.entity = entity;
		this.rangedAttack = rangedAttack;
		this.toolType = type;
		this.moveSpeedAmp = moveSpeedAmp;
		this.attackCooldown = attackCooldown;
		this.maxAttackDistance = p_i47515_5_ * p_i47515_5_;
		this.setMutexBits(3);
	}

	public void setAttackCooldown(int p_189428_1_) {
		this.attackCooldown = p_189428_1_;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return this.entity.getAttackTarget() == null ? false : this.isBowInMainhand();
	}

	protected boolean isBowInMainhand() {
		return this.isTool(this.entity.getHeldItemMainhand());
		//return !this.entity.getHeldItemMainhand().isEmpty() && this.entity.getHeldItemMainhand().getItem() == Items.BOW;
	}

	public boolean isTool(ItemStack item) {

		if (item.isEmpty()) return false;

		if (ProfessionToolManager.getInstance().isTool(toolType, item)) return true;

		return false;

	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return (this.shouldExecute() || !this.entity.getNavigator().noPath()) && this.isBowInMainhand();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		super.startExecuting();
		((IRangedAttackMob) this.rangedAttack).setSwingingArms(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		super.resetTask();
		((IRangedAttackMob) this.rangedAttack).setSwingingArms(false);
		this.seeTime = 0;
		this.attackTime = -1;
		this.entity.resetActiveHand();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask() {
		EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

		if (entitylivingbase != null) {
			double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
			boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase);
			boolean flag1 = this.seeTime > 0;

			if (flag != flag1) {
				this.seeTime = 0;
			}

			if (flag) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			if (d0 <= this.maxAttackDistance && this.seeTime >= 20) {
				this.entity.getNavigator().clearPath();
				++this.strafingTime;
			} else {
				this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
				this.strafingTime = -1;
			}

			if (d0 <= this.maxAttackDistance || entitylivingbase.isDead) {
				this.entity.getNavigator().clearPath();
			}

			if (this.strafingTime >= 20) {
				if (this.entity.getRNG().nextFloat() < 0.3D) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if (this.entity.getRNG().nextFloat() < 0.3D) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (d0 > this.maxAttackDistance * 0.75F) {
					this.strafingBackwards = false;
				} else if (d0 < this.maxAttackDistance * 0.25F) {
					this.strafingBackwards = true;
				}

				this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				this.entity.faceEntity(entitylivingbase, 30.0F, 30.0F);
			} else {
				this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
			}

			if (this.entity.isHandActive()) {
				if (!flag && this.seeTime < -60) {
					this.entity.resetActiveHand();
				} else if (flag) {
					int i = this.entity.getItemInUseMaxCount();

					if (i >= 20) {
						this.entity.resetActiveHand();
						((IRangedAttackMob) this.rangedAttack).attackEntityWithRangedAttack(entitylivingbase, ItemBow.getArrowVelocity(i));
						this.attackTime = this.attackCooldown;
					}
				}
			} else if (--this.attackTime <= 0 && this.seeTime >= -60) {
				this.entity.setActiveHand(EnumHand.MAIN_HAND);
			}
		}
	}
}