package littleservantmod.client.renderer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

/**
 * 汎用的な描画処理をまとめるクラス
 */
public class RendererHelper {

    private final AxisAlignedBB FULL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    /**
     * 半透明な四角を描画
     *
     * @param pos   描画位置
     * @param red   赤
     * @param green 緑
     * @param blue  青
     * @param alpha 透明度
     */
    public void renderFilledBox(BlockPos pos, float red, float green, float blue, float alpha) {

        AxisAlignedBB posAxisAlignedBB = FULL_BLOCK_AABB.offset(pos);

        GlStateManager.pushAttrib();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(0.0F, 1.0F, 0.0F, 0.75F);
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(6.0F);

        RenderGlobal.renderFilledBox(posAxisAlignedBB, red, green, blue, alpha);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        GlStateManager.popAttrib();

    }

    /**
     * PosからEntityまでの線を描画
     *
     * @param start 開始地点
     * @param end   終了地点
     * @param red   赤
     * @param green 緑
     * @param blue  青
     * @param alpha 透明度
     */
    public void renderLine(BlockPos start, Entity end, float red, float green, float blue, float alpha) {

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        //事前設定
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);

        bufferbuilder.pos(start.getX() + 0.5D, start.getY() + 0.5D, start.getZ() + 0.5D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(end.prevPosX, end.prevPosY + (end.height / 2.0D), end.prevPosZ).color(red, green, blue, alpha).endVertex();
        //bufferbuilder.pos(start.getX() + 0.5D, start.getY() + 0.5D, start.getZ() + 0.5D).color(red, green, blue, alpha).endVertex();

        tessellator.draw();


        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        GlStateManager.popAttrib();
        GlStateManager.popMatrix();

    }

}
