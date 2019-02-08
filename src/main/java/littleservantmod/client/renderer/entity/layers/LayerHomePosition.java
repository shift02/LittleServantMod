package littleservantmod.client.renderer.entity.layers;

import javax.annotation.Nullable;

import littleservantmod.client.renderer.RendererHelper;
import littleservantmod.client.renderer.entity.RenderEntityLittleServant;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * サーヴァントのホームポジションの描画
 *
 * @author shift02
 */
public class LayerHomePosition implements LayerWorldRenderer<EntityLittleServant> {

    private final AxisAlignedBB FULL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    protected final RenderEntityLittleServant livingEntityRenderer;

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

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(0.0F, 1.0F, 0.0F, 0.75F);
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(6.0F);

        //座標を修正 (プレイヤー)
        GlStateManager.translate(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);

        //HomePositionの取得
        BlockPos homePosition = entityLittleServant.getHomePosition();

        if (homePosition != null && !homePosition.equals(BlockPos.ORIGIN)) {

            AxisAlignedBB homeAABB = FULL_BLOCK_AABB.offset(homePosition);

            //Homeの描画
            RenderGlobal.renderFilledBox(homeAABB, 0.6F, 0.6F, 1.0F, 0.4F);

            //Home地点からサーヴァントまでの線の描画
            rendererHelper.renderLine(homePosition, entityLittleServant, 0.6F, 0.6F, 1.0F, 0.8F);

        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }


}
