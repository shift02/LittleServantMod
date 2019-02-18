package littleservantmod.client.renderer.entity.layers;

import javax.annotation.Nullable;

import littleservantmod.client.renderer.RendererHelper;
import littleservantmod.client.renderer.entity.RenderEntityLittleServant;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;

/**
 * サーヴァントのホームポジションの描画
 *
 * @author shift02
 */
public class LayerHomePosition implements LayerWorldRenderer<EntityLittleServant> {

    private final RenderEntityLittleServant livingEntityRenderer;

    private final RendererHelper rendererHelper;

    public LayerHomePosition(RenderEntityLittleServant livingEntityRendererIn) {
        this.livingEntityRenderer = livingEntityRendererIn;
        this.rendererHelper = new RendererHelper();
    }

    @Override
    public void doRenderLayer(@Nullable EntityLittleServant entityLittleServant, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
    }

    @Override
    public void doRenderWorldLayer(@Nullable EntityLittleServant entityLittleServant, float partialTicks) {

        if (entityLittleServant == null) return;

        GlStateManager.pushMatrix();

        //座標を修正 (プレイヤー)
        GlStateManager.translate(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);

        //HomePositionの取得
        BlockPos homePosition = entityLittleServant.getHomePosition();

        if (homePosition != null && !homePosition.equals(BlockPos.ORIGIN)) {

            //Homeの描画
            rendererHelper.renderFilledBox(homePosition, 0.6F, 0.6F, 1.0F, 0.4F);

            rendererHelper.renderBoundingBox(homePosition, entityLittleServant.getHomeAndDistance(), 0.0F, 0.0F, 0.0F, 1.0F, partialTicks);

            //Home地点からサーヴァントまでの線の描画
            rendererHelper.renderLine(homePosition, entityLittleServant, 0.0F, 0.0F, 1.0F, 1.0F);

        }


        GlStateManager.popMatrix();

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }


}
