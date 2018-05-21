package littleservantmod.client.renderer.entity;

import littleservantmod.LittleServantMod;
import littleservantmod.client.model.ModelLittleServantBase;
import littleservantmod.entity.EntityLittleServantBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEntityLittleServant extends RenderLivingBase<EntityLittleServantBase> {

	private static final ResourceLocation MAID_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_maid/mob_littlemaid.png");
	private static final ResourceLocation TAMED_MAID_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_maid/mob_littlemaid_tamed.png");

	public RenderEntityLittleServant(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelLittleServantBase(0.0F, false), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLittleServantBase entity) {

		if (entity.isTamed()) {
			return TAMED_MAID_TEXTURES;
		}

		return MAID_TEXTURES;
	}

}