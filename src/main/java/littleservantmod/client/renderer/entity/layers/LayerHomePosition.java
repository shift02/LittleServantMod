package littleservantmod.client.renderer.entity.layers;

import javax.annotation.Nullable;

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

    public LayerHomePosition(RenderEntityLittleServant livingEntityRendererIn) {
        this.livingEntityRenderer = livingEntityRendererIn;
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

        if (homePosition != null) {

            AxisAlignedBB homeAABB = FULL_BLOCK_AABB.offset(homePosition);

            RenderGlobal.renderFilledBox(homeAABB, 1.0F, 1.0F, 1.0F, 0.4F);

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
