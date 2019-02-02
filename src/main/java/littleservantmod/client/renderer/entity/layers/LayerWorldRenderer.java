package littleservantmod.client.renderer.entity.layers;

import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public interface LayerWorldRenderer<E extends EntityLivingBase> extends LayerRenderer<E> {

    public void doRenderWorldLayer(@Nullable EntityLittleServant entityLittleServant, float partialTicks);

}
