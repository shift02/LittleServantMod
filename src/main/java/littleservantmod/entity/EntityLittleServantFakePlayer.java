package littleservantmod.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import littleservantmod.ForgeLSMHooks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

/**
 * サーヴァントにプレイヤーと同じ動作をできるようにするクラス <br />
 * プレイヤー由来の処理もここに
 * @author shift02
 *
 */
public abstract class EntityLittleServantFakePlayer extends EntityLittleServantBlock {

    private EntityPlayer player;

    /** Inventory of the player */
    private InventoryServant inventory;
    private IItemHandler inventoryHandler;

    protected static final DataParameter<Integer> CURRENT_ITEN = EntityDataManager.createKey(EntityLittleServantBase.class, DataSerializers.VARINT);

    public static final net.minecraft.entity.ai.attributes.IAttribute REACH_DISTANCE = new net.minecraft.entity.ai.attributes.RangedAttribute(null, "generic.reachDistance", 5.0D, 0.0D, 1024.0D).setShouldWatch(true);

    private final CooldownTracker cooldownTracker = this.createCooldownTracker();

    protected CooldownTracker createCooldownTracker() {
        return new CooldownTracker();
    }

    public EntityLittleServantFakePlayer(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {

        super.entityInit();

        this.dataManager.register(CURRENT_ITEN, 0);

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.LUCK);
        this.getAttributeMap().registerAttribute(REACH_DISTANCE);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.cooldownTracker.tick();
    }

    /* ======================
     *  インベントリ関係
     * ====================== */

    /**
     * 手に持っているアイテム
     * */
    @Override
    public Iterable<ItemStack> getHeldEquipment() {
        return Lists.newArrayList(this.getHeldItemMainhand(), this.getHeldItemOffhand());
    }

    /**
     * アーマー
     * */
    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.getInventory().armorInventory;
    }

    /**
     * 装備取得処理のブリッジ
     * */
    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        if (slotIn == EntityEquipmentSlot.MAINHAND) {
            return this.getInventory().getCurrentItem();
        } else if (slotIn == EntityEquipmentSlot.OFFHAND) {
            return this.getInventory().offHandInventory.get(0);
        } else {
            return slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR ? this.getInventory().armorInventory.get(slotIn.getIndex()) : ItemStack.EMPTY;
        }
    }

    /**
     * 装備設定処理のブリッジ
     */
    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
        if (slotIn == EntityEquipmentSlot.MAINHAND) {
            this.playEquipSound(stack);
            this.getInventory().mainInventory.set(this.getInventory().currentItem, stack);
        } else if (slotIn == EntityEquipmentSlot.OFFHAND) {
            this.playEquipSound(stack);
            this.getInventory().offHandInventory.set(0, stack);
        } else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
            this.playEquipSound(stack);
            this.getInventory().armorInventory.set(slotIn.getIndex(), stack);
        }
    }

    public EntityPlayer getPlayer() {
        //延滞初期化
        if (this.player == null) {
            if (!world.isRemote) this.player = FakePlayerFactory.getMinecraft((WorldServer) world);
        }
        return player;
    }

    public InventoryServant getInventory() {
        if (this.inventory == null) {
            this.inventory = new InventoryServant((EntityLittleServant) this, this.getPlayer());
        }
        return inventory;
    }

    public IItemHandler getInventoryHandler() {
        if (this.inventoryHandler == null) {
            this.inventoryHandler = new InvWrapper(this.getInventory());
        }
        return inventoryHandler;
    }

    /**
     * Drops an item into the world.
     */
    @Nullable
    public EntityItem dropItem(ItemStack itemStackIn, boolean unused) {
        return ForgeLSMHooks.onPlayerTossEvent((EntityLittleServant) this, itemStackIn, false);
    }

    @Nullable
    public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem) {
        if (droppedItem.isEmpty()) {
            return null;
        } else {
            double d0 = this.posY - 0.30000001192092896D + this.getEyeHeight();
            EntityItem entityitem = new EntityItem(this.world, this.posX, d0, this.posZ, droppedItem);
            entityitem.setPickupDelay(40);

            if (traceItem) {
                entityitem.setThrower(this.getName());
            }

            if (dropAround) {
                float f = this.rand.nextFloat() * 0.5F;
                float f1 = this.rand.nextFloat() * ((float) Math.PI * 2F);
                entityitem.motionX = -MathHelper.sin(f1) * f;
                entityitem.motionZ = MathHelper.cos(f1) * f;
                entityitem.motionY = 0.20000000298023224D;
            } else {
                float f2 = 0.3F;
                entityitem.motionX = -MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
                entityitem.motionZ = MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
                entityitem.motionY = -MathHelper.sin(this.rotationPitch * 0.017453292F) * f2 + 0.1F;
                float f3 = this.rand.nextFloat() * ((float) Math.PI * 2F);
                f2 = 0.02F * this.rand.nextFloat();
                entityitem.motionX += Math.cos(f3) * f2;
                entityitem.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
                entityitem.motionZ += Math.sin(f3) * f2;
            }

            ItemStack itemstack = this.dropItemAndGetStack(entityitem);

            if (traceItem) {
                if (!itemstack.isEmpty()) {
                    //this.addStat(StatList.getDroppedObjectStats(itemstack.getItem()), droppedItem.getCount());
                }

                //this.addStat(StatList.DROP);
            }

            return entityitem;
        }
    }

    public ItemStack dropItemAndGetStack(EntityItem p_184816_1_) {
        if (captureDrops) capturedDrops.add(p_184816_1_);
        else // Forge: Don't indent to keep patch smaller.
            this.world.spawnEntity(p_184816_1_);
        return p_184816_1_.getItem();
    }

    //攻撃方法をプレイヤー風にブリッジ
    @Override
    public boolean attackEntityAsMob(Entity entityIn) {

        this.attackTargetEntityWithCurrentItem(entityIn);

        return super.attackEntityAsMob(entityIn);
    }

    //Itemの使用処理
    public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
        //if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget(this, targetEntity)) return;
        if (targetEntity.canBeAttackedWithItem()) {

            if (!targetEntity.hitByEntity(this)) {
                float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                float f1;

                if (targetEntity instanceof EntityLivingBase) {
                    f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) targetEntity).getCreatureAttribute());
                } else {
                    f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), EnumCreatureAttribute.UNDEFINED);
                }

                float f2 = this.getCooledAttackStrength(0.5F);
                f = f * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;
                this.resetCooldown();

                if (f > 0.0F || f1 > 0.0F) {
                    boolean flag = f2 > 0.9F;
                    boolean flag1 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockbackModifier(this);

                    if (this.isSprinting() && flag) {
                        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, this.getSoundCategory(), 1.0F, 1.0F);
                        ++i;
                        flag1 = true;
                    }

                    boolean flag2 = flag && this.fallDistance > 0.0F && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(MobEffects.BLINDNESS) && !this.isRiding()
                            && targetEntity instanceof EntityLivingBase;
                    flag2 = flag2 && !this.isSprinting();

                    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(this.player, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
                    flag2 = hitResult != null;
                    if (flag2) {
                        f *= hitResult.getDamageModifier();
                    }

                    f = f + f1;
                    boolean flag3 = false;
                    double d0 = this.distanceWalkedModified - this.prevDistanceWalkedModified;

                    if (flag && !flag2 && !flag1 && this.onGround && d0 < this.getAIMoveSpeed()) {
                        ItemStack itemstack = this.getHeldItem(EnumHand.MAIN_HAND);

                        if (itemstack.getItem() instanceof ItemSword) {
                            flag3 = true;
                        }
                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(this);

                    if (targetEntity instanceof EntityLivingBase) {
                        f4 = ((EntityLivingBase) targetEntity).getHealth();

                        if (j > 0 && !targetEntity.isBurning()) {
                            flag4 = true;
                            targetEntity.setFire(1);
                        }
                    }

                    double d1 = targetEntity.motionX;
                    double d2 = targetEntity.motionY;
                    double d3 = targetEntity.motionZ;
                    //TODO
                    boolean flag5 = targetEntity.attackEntityFrom(DamageSource.CACTUS, f);

                    if (flag5) {
                        if (i > 0) {
                            if (targetEntity instanceof EntityLivingBase) {
                                ((EntityLivingBase) targetEntity).knockBack(this, i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                            } else {
                                targetEntity.addVelocity(-MathHelper.sin(this.rotationYaw * 0.017453292F) * i * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 0.017453292F) * i * 0.5F);
                            }

                            this.motionX *= 0.6D;
                            this.motionZ *= 0.6D;
                            this.setSprinting(false);
                        }

                        if (flag3) {
                            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this) * f;

                            for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, targetEntity.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
                                if (entitylivingbase != this && entitylivingbase != targetEntity && !this.isOnSameTeam(entitylivingbase) && this.getDistanceSq(entitylivingbase) < 9.0D) {
                                    entitylivingbase.knockBack(this, 0.4F, MathHelper.sin(this.rotationYaw * 0.017453292F), (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                                    //TODO
                                    entitylivingbase.attackEntityFrom(DamageSource.CACTUS, f3);
                                }
                            }

                            this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                            this.spawnSweepParticles();
                        }

                        if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
                            ((EntityPlayerMP) targetEntity).connection.sendPacket(new SPacketEntityVelocity(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.motionX = d1;
                            targetEntity.motionY = d2;
                            targetEntity.motionZ = d3;
                        }

                        if (flag2) {
                            this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0F, 1.0F);
                            this.onCriticalHit(targetEntity);
                        }

                        if (!flag2 && !flag3) {
                            if (flag) {
                                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0F, 1.0F);
                            } else {
                                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }

                        if (f1 > 0.0F) {
                            this.onEnchantmentCritical(targetEntity);
                        }

                        this.setLastAttackedEntity(targetEntity);

                        if (targetEntity instanceof EntityLivingBase) {
                            EnchantmentHelper.applyThornEnchantments((EntityLivingBase) targetEntity, this);
                        }

                        EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
                        ItemStack itemstack1 = this.getHeldItemMainhand();
                        Entity entity = targetEntity;

                        if (targetEntity instanceof MultiPartEntityPart) {
                            IEntityMultiPart ientitymultipart = ((MultiPartEntityPart) targetEntity).parent;

                            if (ientitymultipart instanceof EntityLivingBase) {
                                entity = (EntityLivingBase) ientitymultipart;
                            }
                        }

                        if (!itemstack1.isEmpty() && entity instanceof EntityLivingBase) {
                            ItemStack beforeHitCopy = itemstack1.copy();
                            itemstack1.hitEntity((EntityLivingBase) entity, this.player);

                            if (itemstack1.isEmpty()) {
                                //net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this, beforeHitCopy, EnumHand.MAIN_HAND);
                                this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }

                        if (targetEntity instanceof EntityLivingBase) {
                            float f5 = f4 - ((EntityLivingBase) targetEntity).getHealth();
                            //this.addStat(StatList.DAMAGE_DEALT, Math.round(f5 * 10.0F));

                            if (j > 0) {
                                targetEntity.setFire(j * 4);
                            }

                            if (this.world instanceof WorldServer && f5 > 2.0F) {
                                int k = (int) (f5 * 0.5D);
                                ((WorldServer) this.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.posX, targetEntity.posY + targetEntity.height * 0.5F, targetEntity.posZ, k, 0.1D, 0.0D,
                                        0.1D, 0.2D);
                            }
                        }

                        //this.addExhaustion(0.1F);
                    } else {
                        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, this.getSoundCategory(), 1.0F, 1.0F);

                        if (flag4) {
                            targetEntity.extinguish();
                        }
                    }
                }
            }
        }
    }

    public float getCooldownPeriod() {
        return (float) (1.0D / this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * 20.0D);
    }

    /**
     * Returns the percentage of attack power available based on the cooldown (zero to one).
     */
    public float getCooledAttackStrength(float adjustTicks) {
        return MathHelper.clamp((this.ticksSinceLastSwing + adjustTicks) / this.getCooldownPeriod(), 0.0F, 1.0F);
    }

    public void resetCooldown() {
        this.ticksSinceLastSwing = 0;
    }

    public void onCriticalHit(Entity entityHit) {
        if (this.world instanceof WorldServer) {
            ((WorldServer) this.world).getEntityTracker().sendToTrackingAndSelf(this, new SPacketAnimation(entityHit, 4));
        }
    }

    public void onEnchantmentCritical(Entity entityHit) {
        if (this.world instanceof WorldServer) {
            ((WorldServer) this.world).getEntityTracker().sendToTrackingAndSelf(this, new SPacketAnimation(entityHit, 5));
        }
    }

    public void spawnSweepParticles() {
        double d0 = (-MathHelper.sin(this.rotationYaw * 0.017453292F));
        double d1 = MathHelper.cos(this.rotationYaw * 0.017453292F);

        if (this.world instanceof WorldServer) {
            ((WorldServer) this.world).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX + d0, this.posY + this.height * 0.5D, this.posZ + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    public CooldownTracker getCooldownTracker() {
        return this.cooldownTracker;
    }

    public int getCurrentItem() {
        return this.dataManager.get(CURRENT_ITEN);
    }

    public void setCurrentItem(int currentItem) {
        this.getInventory().currentItem = currentItem;
        this.dataManager.set(CURRENT_ITEN, currentItem);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);

        if (CURRENT_ITEN.equals(key) && this.world.isRemote) {

            this.getInventory().currentItem = getCurrentItem();

        }

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
        this.getInventory().readFromNBT(nbttaglist);
        this.getInventory().currentItem = compound.getInteger("SelectedItemSlot");

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setTag("Inventory", this.getInventory().writeToNBT(new NBTTagList()));
        //		compound.setInteger("SelectedItemSlot", this.getInventory().currentItem);
        compound.setInteger("SelectedItemSlot", this.getCurrentItem());

    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == null) {
            return (T) getInventoryHandler();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
}
