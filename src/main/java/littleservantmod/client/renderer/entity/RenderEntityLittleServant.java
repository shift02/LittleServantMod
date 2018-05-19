package littleservantmod.client.renderer.entity;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntityLittleServant extends RenderLivingBase{

	public RenderEntityLittleServant(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelPlayer(0.0F, false), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return  new ResourceLocation("");
	}

}
