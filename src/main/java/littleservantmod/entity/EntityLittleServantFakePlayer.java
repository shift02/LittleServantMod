package littleservantmod.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

/**
 * サーヴァントにプレイヤーと同じ動作をできるようにするクラス
 * @author shift02
 *
 */
public abstract class EntityLittleServantFakePlayer extends EntityLittleServantBase {

	protected EntityPlayer player;

	public EntityLittleServantFakePlayer(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {

		super.entityInit();

		if (!world.isRemote) this.player = FakePlayerFactory.getMinecraft((WorldServer) world);

	}

	public EntityPlayer getPlayer() {
		return player;
	}

}
