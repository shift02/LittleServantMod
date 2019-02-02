package littleservantmod.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import littleservantmod.api.IServant;
import littleservantmod.entity.ai.EntityAISit;
import littleservantmod.entity.ai.EntityAIWander;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * バニラからのメソッドや処理はこちらに実装する
 *
 * @author shift02
 */
public abstract class EntityLittleServantBase extends EntityLiving implements IServant, INpc {

    protected EntityAIWander wander;

    /*
     * EntityCreature
     */
    public static final UUID FLEEING_SPEED_MODIFIER_UUID = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
    public static final AttributeModifier FLEEING_SPEED_MODIFIER = (new AttributeModifier(FLEEING_SPEED_MODIFIER_UUID,
            "Fleeing speed bonus", 2.0D, 2)).setSaved(false);

    /**
     * If -1 there is no maximum distance
     */
    private float maximumHomeDistance = -1.0F;

    /*
     * EntityTameable
     */
    protected static final DataParameter<Boolean> TAMED = EntityDataManager.<Boolean>createKey(EntityLittleServantBase.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(EntityLittleServantBase.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    protected static final DataParameter<Boolean> SITED = EntityDataManager.<Boolean>createKey(EntityLittleServantBase.class, DataSerializers.BOOLEAN);

    public EntityAISit aiSit;

    //同期
    protected static final DataParameter<BlockPos> HOME = EntityDataManager.<BlockPos>createKey(EntityLittleServantBase.class, DataSerializers.BLOCK_POS);

    public EntityLittleServantBase(World worldIn) {
        super(worldIn);

        this.height = 1.53F;

    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TAMED, Boolean.FALSE);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
        this.dataManager.register(SITED, Boolean.FALSE);

        this.dataManager.register(HOME, BlockPos.ORIGIN);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);

    }

    /*
     * EntityCreature
     */

    /**
     * Applies logic related to leashes, for example dragging the entity or breaking the leash.
     */
    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();

        if (this.getLeashed() && this.getLeashHolder() != null && this.getLeashHolder().world == this.world) {
            Entity entity = this.getLeashHolder();
            this.setHomePosAndDistance(new BlockPos((int) entity.posX, (int) entity.posY, (int) entity.posZ), 5);
            float f = this.getDistance(entity);

            /*
            if (this instanceof EntityTameable && ((EntityTameable) this).isSitting()) {
            	if (f > 10.0F) {
            		this.clearLeashed(true, true);
            	}
            
            	return;
            }*/

            this.onLeashDistance(f);

            if (f > 10.0F) {
                this.clearLeashed(true, true);
                this.tasks.disableControlFlag(1);
            } else if (f > 6.0F) {
                double d0 = (entity.posX - this.posX) / f;
                double d1 = (entity.posY - this.posY) / f;
                double d2 = (entity.posZ - this.posZ) / f;
                this.motionX += d0 * Math.abs(d0) * 0.4D;
                this.motionY += d1 * Math.abs(d1) * 0.4D;
                this.motionZ += d2 * Math.abs(d2) * 0.4D;
            } else {
                this.tasks.enableControlFlag(1);
                float f1 = 2.0F;
                Vec3d vec3d = (new Vec3d(entity.posX - this.posX, entity.posY - this.posY, entity.posZ - this.posZ))
                        .normalize().scale(Math.max(f - 2.0F, 0.0F));
                this.getNavigator().tryMoveToXYZ(this.posX + vec3d.x, this.posY + vec3d.y, this.posZ + vec3d.z,
                        this.followLeashSpeed());
            }
        }
    }

    public float getBlockPathWeight(BlockPos pos) {
        return 4.0F;
    }

    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistanceFromPosition(new BlockPos(this));
    }

    public boolean isWithinHomeDistanceFromPosition(BlockPos pos) {
        if (this.maximumHomeDistance == -1.0F) {
            return true;
        } else {
            return this.getHomePosition().distanceSq(pos) < this.maximumHomeDistance * this.maximumHomeDistance;
        }
    }

    /**
     * Sets home position and max distance for it
     */
    public void setHomePosAndDistance(BlockPos pos, int distance) {
        this.dataManager.set(HOME, pos);
        this.maximumHomeDistance = distance;
    }

    public void clearHomePosition() {
        this.dataManager.set(HOME, BlockPos.ORIGIN);
    }

    public BlockPos getHomePosition() {
        return this.dataManager.get(HOME);
    }

    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }

    public void detachHome() {
        this.maximumHomeDistance = -1.0F;
    }

    /**
     * Returns whether a home area is defined for this entity.
     */
    public boolean hasHome() {
        return this.maximumHomeDistance != -1.0F;
    }

    protected double followLeashSpeed() {
        return 1.0D;
    }

    protected void onLeashDistance(float p_142017_1_) {
    }

    /*
     * EntityTameable
     */
    public boolean isTamed() {
        return this.dataManager.get(TAMED);
    }

    private void setTamed(Boolean tamed) {

        this.dataManager.set(TAMED, tamed);

    }

    public boolean isSitting() {
        return this.dataManager.get(SITED);
    }

    public void setSitting(boolean sitting) {
        this.dataManager.set(SITED, sitting);
    }

    @Nullable
    public UUID getOwnerId() {
        return (UUID) ((Optional) this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }

    @Override
    @Nullable
    public EntityLivingBase getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public boolean isOwner(EntityLivingBase entityIn) {
        return entityIn == this.getOwner();
    }

    public void setTamedBy(EntityPlayer player) {
        this.setTamed(true);
        this.setOwnerId(player.getUniqueID());

        //if (player instanceof EntityPlayerMP) {
        //CriteriaTriggers.TAME_ANIMAL.trigger((EntityPlayerMP) player, this);
        //}
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);

        if (TAMED.equals(key) && this.world.isRemote) {

            playTameEffect(this.dataManager.get(TAMED));

        }

    }

    protected void playTameEffect(boolean play) {
        EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;

        if (!play) enumparticletypes = EnumParticleTypes.SMOKE_NORMAL;

        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(enumparticletypes, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height,
                    this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
        }
    }

    /*
     * EntityMob
     */
    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        super.onLivingUpdate();
    }

    /*
     * NBT
     */

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        if (this.getOwnerId() == null) {
            compound.setString("OwnerUUID", "");
        } else {
            compound.setString("OwnerUUID", this.getOwnerId().toString());
        }

        compound.setBoolean("Sitting", this.isSitting());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        String s;

        if (compound.hasKey("OwnerUUID", 8)) {
            s = compound.getString("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }

        if (!s.isEmpty()) {
            try {
                this.setOwnerId(UUID.fromString(s));
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }

        if (this.aiSit != null) {
            this.aiSit.setSitting(compound.getBoolean("Sitting"));
        }

        this.setSitting(compound.getBoolean("Sitting"));

    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.85F;
    }

}
