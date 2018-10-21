package littleservantmod.client.model;

import littleservantmod.entity.EntityLittleServantBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelLittleServantMaid extends ModelLittleServantBase {

    public ModelRenderer mainFrame;

    public ModelRenderer HeadMount;
    public ModelRenderer HeadTop;

    public ModelRenderer bipedTorso;
    public ModelRenderer bipedNeck;

    public ModelRenderer bipedPelvic;

    //腕
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;

    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;
    private final ModelRenderer bipedCape;
    private final ModelRenderer bipedDeadmau5Head;
    private final boolean smallArms;

    //スカート
    public ModelRenderer bipedSkirt;

    public ModelLittleServantMaid(float modelSize, boolean smallArmsIn) {
        super(modelSize, 0.0F, 64, 32);

        this.smallArms = smallArmsIn;
        this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
        this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, modelSize);
        this.bipedCape = new ModelRenderer(this, 0, 0);
        this.bipedCape.setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);

        //本体の初期化
        this.bipedBody = new ModelRenderer(this, 32, 8);
        this.bipedBody.addBox(-3F, 0F, -2F, 6, 7, 4, modelSize);
        this.bipedBody.setRotationPoint(0F, 0F, 0F);

        //頭
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8, modelSize); // Head
        /*this.bipedHead.setTextureOffset(24,  0).addBox(-4F, 0F, 1F, 8, 4, 3, modelSize);			// Hire
        this.bipedHead.setTextureOffset(24, 18).addBox(-5F, -7F, 0.2F, 1, 3, 3, modelSize);		// ChignonR
        this.bipedHead.setTextureOffset(24, 18).addBox(4F, -7F, 0.2F, 1, 3, 3, modelSize);		// ChignonL
        this.bipedHead.setTextureOffset(52, 10).addBox(-2F, -7.2F, 4F, 4, 4, 2, modelSize);		// ChignonB
        this.bipedHead.setTextureOffset(46, 20).addBox(-1.5F, -6.8F, 4F, 3, 9, 3, modelSize);	// Tail
        this.bipedHead.setTextureOffset(58, 21).addBox(-5.5F, -6.8F, 0.9F, 1, 8, 2, modelSize);	// SideTailR
        */
        //this.bipedHead.mirror =true;
        //this.bipedHead.setTextureOffset(58, 21).addBox(4.5F, -6.8F, 0.9F, 1, 8, 2, modelSize);	// SideTailL
        this.bipedHead.setRotationPoint(0F, 0F, 0F);

        /*
        if (smallArmsIn)
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
        }
        else
        {*/
        bipedRightArm = new ModelRenderer(this, 48, 0);
        bipedRightArm.addBox(0.0F, -1F, -1F, 2, 8, 2, modelSize);
        bipedRightArm.setRotationPoint(-2.0F, 1.5F, 0F);

        bipedLeftArm = new ModelRenderer(this, 56, 0);
        bipedLeftArm.addBox(-2.0F, -1F, -1F, 2, 8, 2, modelSize);
        bipedLeftArm.setRotationPoint(2.0F, 1.5F, 0F);

        this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
        this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
        this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);

        //}

        this.bipedRightLeg = new ModelRenderer(this, 32, 19);
        this.bipedRightLeg.addBox(-2F, 0F, -2F, 3, 9, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1F, 0F, 0F);

        this.bipedLeftLeg = new ModelRenderer(this, 32, 19);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1F, 0F, -2F, 3, 9, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1F, 0F, 0F);

        this.bipedSkirt = new ModelRenderer(this, 0, 16);
        this.bipedSkirt.addBox(-4F, -2F, -4F, 8, 8, 8, modelSize);
        //this.bipedSkirt.setRotationPoint(0F, 0F, 0F);
        this.bipedSkirt.setRotationPoint(0F, 12F, 0F);

        //いらない
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
        this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
        //ここまで

        bipedTorso = new ModelRenderer(this);
        bipedNeck = new ModelRenderer(this);
        bipedPelvic = new ModelRenderer(this);
        //bipedPelvic.setRotationPoint(0F, 7F, 0F);
        bipedPelvic.setRotationPoint(0F, -5F, 0F);
        mainFrame = new ModelRenderer(this, 0, 0);
        mainFrame.setRotationPoint(0F, 0F + 8F, 0F);
        mainFrame.addChild(bipedTorso);
        bipedTorso.addChild(bipedNeck);
        bipedTorso.addChild(bipedBody);
        bipedTorso.addChild(bipedPelvic);
        bipedNeck.addChild(bipedHead);
        bipedNeck.addChild(bipedRightArm);
        bipedNeck.addChild(bipedLeftArm);
        //bipedHead.addChild(HeadMount);
        //bipedHead.addChild(HeadTop);
        //bipedRightArm.addChild(Arms[0]);
        //bipedLeftArm.addChild(Arms[1]);
        bipedPelvic.addChild(bipedRightLeg);
        bipedPelvic.addChild(bipedLeftLeg);
        bipedPelvic.addChild(bipedSkirt);

    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();

        mainFrame.render(scale);

        GlStateManager.popMatrix();

        if (true) return;

        if (this.isChild) {
            float f = 2.0F;
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);
        } else {
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);
        }

        GlStateManager.popMatrix();
    }

    public void renderDeadmau5Head(float scale) {
        copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
        this.bipedDeadmau5Head.rotationPointX = 0.0F;
        this.bipedDeadmau5Head.rotationPointY = 0.0F;
        this.bipedDeadmau5Head.render(scale);
    }

    public void renderCape(float scale) {
        this.bipedCape.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        copyModelAngles(this.bipedBody, this.bipedBodyWear);

        if (entityIn.isSneaking()) {
            this.bipedCape.rotationPointY = 2.0F;
        } else {
            this.bipedCape.rotationPointY = 0.0F;
        }

        //Siting時の処理
        if (((EntityLittleServantBase) entityIn).isSitting()) {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 3F);
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 3F);
            //this.bipedRightArm.rotateAngleZ = 10;

            this.bipedRightArm.rotateAngleY = -0.5235988F + 0.1f;
            this.bipedLeftArm.rotateAngleY = 0.5235988F - 0.1f;

        } else {

        }

        //足の動きを小さく
        this.setSmallRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

    }

    private void setSmallRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch, float scaleFactor, Entity entityIn) {

        boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getTicksElytraFlying() > 4;

        float f = 1.0F;

        if (flag) {
            f = (float) (entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        limbSwingAmount *= 0.5;

        if (!this.isRiding) {
            this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
            this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
        }

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.bipedLeftArmwear.showModel = visible;
        this.bipedRightArmwear.showModel = visible;
        this.bipedLeftLegwear.showModel = visible;
        this.bipedRightLegwear.showModel = visible;
        this.bipedBodyWear.showModel = visible;
        this.bipedCape.showModel = visible;
        this.bipedDeadmau5Head.showModel = visible;
    }

    @Override
    public void postRenderArm(float scale, EnumHandSide side) {
        ModelRenderer modelrenderer = this.getArmForSide(side);

        if (this.smallArms) {
            float f = 0.5F * (side == EnumHandSide.RIGHT ? 1 : -1);
            modelrenderer.rotationPointX += f;
            modelrenderer.postRender(scale);
            modelrenderer.rotationPointX -= f;
        } else {
            modelrenderer.postRender(scale);
        }
    }
}