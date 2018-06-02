package littleservantmod.util;

import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.EntityLittleServantBase;
import net.minecraft.world.World;

public class OpenGuiEntityId {

	//X
	public int entityId;

	//Y
	public int dimensionId;

	public OpenGuiEntityId(EntityLittleServantBase servant) {

		this.entityId = servant.getEntityId();

		this.dimensionId = servant.world.provider.getDimension();

	}

	public static EntityLittleServant getEntityFromXYZ(World world, int x, int y, int z) {

		return (EntityLittleServant) world.getEntityByID(x);

	}

	public int getX() {
		return this.entityId;
	}

}
