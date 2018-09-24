package littleservantmod.entity.ai;

import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import littleservantmod.api.ISugarItemHandler;
import littleservantmod.api.LittleServantModAPI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class EntityAIUseSugar extends EntityAIBase {

    private final EntityLiving servant;

    private final IItemHandlerModifiable inventory;

    protected Random rand;

    public EntityAIUseSugar(EntityLiving servant) {

        if (!servant.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
            throw new IllegalArgumentException();

        this.inventory = (IItemHandlerModifiable) servant.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.servant = servant;

        this.rand = new Random();

    }

    @Override
    public boolean shouldExecute() {

        if (servant.getHealth() >= 20) return false;

        return IntStream.range(0, this.inventory.getSlots())
                .parallel()
                .mapToObj(i -> Pair.of(i, this.inventory.getStackInSlot(i)))
                .filter(p -> LittleServantModAPI.register.getISugarItemHandlerList()
                        .stream()
                        .filter(isih -> isih.canUse(p.getRight()))
                        .findAny()
                        .isPresent())
                .findAny()
                .isPresent();

    }

    @Override
    public void updateTask() {

        IntStream.range(0, this.inventory.getSlots())
                .parallel()
                .mapToObj(i -> Pair.of(i, this.inventory.getStackInSlot(i)))
                .filter(p -> LittleServantModAPI.register.getISugarItemHandlerList()
                        .stream()
                        .filter(isih -> isih.canUse(p.getRight()))
                        .findAny()
                        .isPresent())
                .findFirst()
                .ifPresent(p -> {

                    if (servant.getHealth() >= 20) return;

                    for (ISugarItemHandler sih : LittleServantModAPI.register.getISugarItemHandlerList()) {

                        if (sih.canUse(p.getRight())) {
                            servant.heal(sih.useItem(p.getRight()));
                            playSitEffect();
                        }

                    }

                });

    }

    protected void playSitEffect() {

        servant.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);

        EnumParticleTypes enumparticletypes = EnumParticleTypes.NOTE;

        for (int i = 0; i < 1; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;

            ((WorldServer) servant.world).spawnParticle(enumparticletypes, servant.posX + this.rand.nextFloat() * servant.width * 2.0F - servant.width, servant.posY + 0.9D + this.rand.nextFloat() * servant.height,
                    servant.posZ + this.rand.nextFloat() * servant.width * 2.0F - servant.width, 1, d0, d1, d2, 0.0d);

            /*
            ((WorldServer) servant.world).spawnParticle(enumparticletypes, servant.posX + this.rand.nextFloat() * servant.width * 2.0F - servant.width, servant.posY + 0.9D + this.rand.nextFloat() * servant.height,
                    servant.posZ + this.rand.nextFloat() * servant.width * 2.0F - servant.width, d0, d1, d2);*/
        }
    }

}
