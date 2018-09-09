package littleservantmod.entity.ai;

import littleservantmod.entity.EntityLittleServantFakePlayer;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class EntityAIEquip extends EntityAIBase {

	private final EntityLittleServantFakePlayer servant;
	private final int defaultIndex;
	private final EnumHand hand;
	private final Predicate<ItemStack> shouldEquip;

	public EntityAIEquip(EntityLittleServantFakePlayer servant, int defaultIndex, EnumHand hand, Predicate<ItemStack> shouldEquip) {
		this.servant = servant;
		this.defaultIndex = defaultIndex;
		this.hand = hand;
		this.shouldEquip = shouldEquip;
	}

	public EntityAIEquip(EntityLittleServantFakePlayer servant, EnumHand hand, Predicate<ItemStack> shouldEquip) {
		this(servant, -1, hand, shouldEquip);
	}

	@Override
	public boolean shouldExecute() {

		return !this.shouldEquip.test(this.servant.inventory.getItemInHand(hand));
	}

	@Override
	public void updateTask() {

		int index = IntStream.range(0, this.servant.inventory.mainInventory.size())
				.parallel()
				.filter(i -> this.shouldEquip.test(this.servant.inventory.mainInventory.get(i)))
				.findAny()
				.orElse(defaultIndex);

		this.servant.setItemInHand(index, hand);

	}

}
