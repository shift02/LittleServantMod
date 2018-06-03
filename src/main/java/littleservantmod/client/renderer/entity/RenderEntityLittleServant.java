package littleservantmod.client.renderer.entity;

import littleservantmod.LittleServantMod;
import littleservantmod.client.model.ModelLittleServantBase;
import littleservantmod.client.renderer.entity.layers.LayerCustomHead;
import littleservantmod.client.renderer.entity.layers.LayerHeldItem;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEntityLittleServant extends RenderLivingBase<EntityLittleServant> {

	private static final ResourceLocation MAID_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_maid/mob_littlemaid.png");
	private static final ResourceLocation TAMED_MAID_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_maid/mob_littlemaid_tamed.png");

	public RenderEntityLittleServant(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelLittleServantBase(0.0F, false), 0.5F);

		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));

	}

	@Override
	public ModelLittleServantBase getMainModel() {
		return (ModelLittleServantBase) super.getMainModel();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLittleServant entity) {

		if (entity.isTamed()) {
			return TAMED_MAID_TEXTURES;
		}

		return MAID_TEXTURES;
	}

	@Override
	public void renderName(EntityLittleServant entity, double x, double y, double z) {
		if (this.canRenderName(entity) && !entity.isGui) {
			this.renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), x, y, z, 64);
		}
	}

}
