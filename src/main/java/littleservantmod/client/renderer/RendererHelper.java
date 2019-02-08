package littleservantmod.client.renderer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

/**
 * 汎用的な描画処理をまとめるクラス
 */
public class RendererHelper {

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

        /*
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        //事前設定
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);*/

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);

        bufferbuilder.pos(start.getX() + 0.5D, start.getY() + 0.5D, start.getZ() + 0.5D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(end.prevPosX, end.prevPosY + (end.height / 2.0D), end.prevPosZ).color(red, green, blue, alpha).endVertex();
        //bufferbuilder.pos(start.getX() + 0.5D, start.getY() + 0.5D, start.getZ() + 0.5D).color(red, green, blue, alpha).endVertex();

        tessellator.draw();

        /*
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        GlStateManager.popAttrib();
        GlStateManager.popMatrix();*/

    }

}
