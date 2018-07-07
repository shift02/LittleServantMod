package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServantFakePlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityAIUseItemForEntity extends EntityAIAttackMelee2 {

	public EntityAIUseItemForEntity(EntityLittleServantFakePlayer servant, double speedIn, boolean useLongMemory) {
		super(servant, speedIn, useLongMemory);
	}

	@Override
	protected void checkAndPerformAttack(EntityLivingBase target, double p_190102_2_) {
		double d0 = this.getAttackReachSqr(target);

		if (p_190102_2_ <= d0 && this.attackTick <= 0) {
			this.attackTick = 10;
			this.attacker.swingArm(EnumHand.MAIN_HAND);
			//this.attacker.attackEntityAsMob(target);
			boolean result = this.attacker.getHeldItemMainhand().interactWithEntity(this.attacker.player, target, EnumHand.MAIN_HAND);

			if (result) {
				this.attacker.setAttackTarget(null);
			}

			//processRightClick(this.attacker, this.attacker.world, this.attacker.getHeldItemMainhand(), EnumHand.MAIN_HAND);
		}
	}

	public EnumActionResult processRightClick(EntityLittleServantFakePlayer servant, World worldIn, ItemStack stack, EnumHand hand) {

		if (servant.getCooldownTracker().hasCooldown(stack.getItem())) {
			return EnumActionResult.PASS;
		} else {
			EnumActionResult cancelResult = net.minecraftforge.common.ForgeHooks.onItemRightClick(servant.player, hand);
			if (cancelResult != null) return cancelResult;
			int i = stack.getCount();
			int j = stack.getMetadata();
			ItemStack copyBeforeUse = stack.copy();
			ActionResult<ItemStack> actionresult = stack.useItemRightClick(worldIn, servant.player, hand);
			ItemStack itemstack = actionresult.getResult();

			if (itemstack == stack && itemstack.getCount() == i && itemstack.getMaxItemUseDuration() <= 0 && itemstack.getMetadata() == j) {
				return actionresult.getType();
			} else if (actionresult.getType() == EnumActionResult.FAIL && itemstack.getMaxItemUseDuration() > 0 && !servant.isHandActive()) {
				return actionresult.getType();
			} else {
				servant.setHeldItem(hand, itemstack);

				/*
				if (this.isCreative()) {
					itemstack.setCount(i);
				
					if (itemstack.isItemStackDamageable()) {
						itemstack.setItemDamage(j);
					}
				}*/

				if (itemstack.isEmpty()) {
					net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(servant.player, copyBeforeUse, hand);
					servant.setHeldItem(hand, ItemStack.EMPTY);
				}

				//if (!player.isHandActive()) {
				//	((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
				//}

				return actionresult.getType();
			}
		}
	}

}
