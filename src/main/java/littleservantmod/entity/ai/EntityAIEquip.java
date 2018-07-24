package littleservantmod.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class EntityAIEquip extends EntityAIBase {

	private final IItemHandlerModifiable inventory;
	private final IItemHandlerModifiable equipments;
	private final EntityEquipmentSlot equipmentSlot;
	private final Predicate<ItemStack> shouldEquip;

	public EntityAIEquip(ICapabilityProvider servant, EntityEquipmentSlot equipmentSlot, Predicate<ItemStack> shouldEquip) {

		if (!servant.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
			throw new IllegalArgumentException();
		this.inventory = (IItemHandlerModifiable) servant.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		this.equipments = (IItemHandlerModifiable) servant.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.VALUES[equipmentSlot.ordinal()]);
		this.equipmentSlot = equipmentSlot;
		this.shouldEquip = shouldEquip;

	}

	@Override
	public boolean shouldExecute() {

		return !this.shouldEquip.test(this.equipments.getStackInSlot(this.equipmentSlot.getIndex()));
	}

	@Override
	public void updateTask() {

		IntStream.range(0, this.inventory.getSlots())
				.parallel()
				.mapToObj(i -> Pair.of(i, this.inventory.getStackInSlot(i)))
				.filter(p -> this.shouldEquip.test(p.getRight()))
				.findAny()
				.ifPresent(p -> {
					this.inventory.setStackInSlot(p.getLeft(), this.equipments.getStackInSlot(this.equipmentSlot.getIndex()));
					this.equipments.setStackInSlot(this.equipmentSlot.getIndex(), p.getRight());
				});

	}

}
