package littleservantmod.entity;

import littleservantmod.entity.ai.EntityAIFollow;
import littleservantmod.entity.ai.EntityAISit;
import littleservantmod.entity.ai.EntityAITempt;
import littleservantmod.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityLittleServant extends EntityLittleServantBase {

	public EntityLittleServant(World worldIn) {
		super(worldIn);

	}

	@Override
	protected void initEntityAI() {

		//排他処理メモ
		//1 = 適当にウロウロ, 2 = 何かを見つめる, 3 = 何かについていく, 5 = 停止

		// うろうろ移動するAIの追加
		//this.wander = new EntityAIWander(this, interpTargetPitch);
		//this.tasks.addTask(1, this.wander);

		this.aiSit = new EntityAISit(this);

		this.tasks.addTask(2, this.aiSit);

		//砂糖についてくる
		this.tasks.addTask(3, new EntityAITempt(this, 0.5D, Items.SUGAR, false));

		this.tasks.addTask(5, new EntityAIFollow(this, EntityRabbit.class, 0.5D));

		//ウロウロ
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.5D));

		//うさぎを見る
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityRabbit.class, 4.0F));

		//プレイヤーを見る
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		// 見回すAIの追加
		this.tasks.addTask(10, new EntityAILookIdle(this));

	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		boolean flag = !itemstack.isEmpty();

		if (flag && itemstack.getItem() == Items.SPAWN_EGG) {
			return super.processInteract(player, hand);
		}

		if (flag && itemstack.getItem() == Items.CAKE) {
			return this.processInteractCake(player, hand, itemstack);
		}

		if (this.isTamed()) {
			return this.processInteractTamed(player, hand, itemstack);
		}

		return true;

	}

	public boolean processInteractCake(EntityPlayer player, EnumHand hand, ItemStack itemstack) {

		if (this.isTamed()) {
			return false;
		}

		if (!player.capabilities.isCreativeMode) {
			itemstack.shrink(1);
		}

		if (!this.world.isRemote) {

			if (this.rand.nextInt(3) == 0 || true) {
				this.setTamedBy(player);
				this.navigator.clearPath();
				this.setAttackTarget((EntityLivingBase) null);
				this.aiSit.setSitting(true);
				this.setHealth(20.0F);
				this.playTameEffect(true);
				this.world.setEntityState(this, (byte) 7);
			} else {
				this.playTameEffect(false);
				this.world.setEntityState(this, (byte) 6);
			}

		}

		return true;

	}

	public boolean processInteractTamed(EntityPlayer player, EnumHand hand, ItemStack itemstack) {

		if (this.isOwner(player) && itemstack.getItem().equals(Items.SUGAR)) {

			if (!this.world.isRemote) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPath();
				this.setAttackTarget((EntityLivingBase) null);

			} else {
				this.playSitEffect(!this.isSitting());
			}

			return true;

		}

		return true;

	}

	protected void playSitEffect(boolean play) {
		EnumParticleTypes enumparticletypes = EnumParticleTypes.NOTE;

		if (!play) enumparticletypes = EnumParticleTypes.SPELL_WITCH;

		for (int i = 0; i < 4; ++i) {
			double d0 = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.world.spawnParticle(enumparticletypes, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.9D + this.rand.nextFloat() * this.height,
					this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
		}
	}

}
