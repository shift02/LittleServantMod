package littleservantmod.client.gui.inventory;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import littleservantmod.ForgeLSMHooks;
import littleservantmod.LittleServantMod;
import littleservantmod.entity.EntityLittleServant;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiServantInventory extends GuiSideTabContainer {

	/** The location of the inventory background texture */
	public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation(LittleServantMod.MOD_ID, "textures/guis/container/littleservant_inventory.png");

	/** The old x position of the mouse pointer */
	private float oldMouseX;
	/** The old y position of the mouse pointer */
	private float oldMouseY;

	public EntityLittleServant servant;

	private Random rand;
	private int updateCounter;

	/** Used with updateCounter to make the heart bar flash */
	protected long healthUpdateCounter;

	protected int servantHealth;
	protected int lastServantHealth;

	/** The last recorded system time */
	protected long lastSystemTime;

	public GuiServantInventory(EntityLittleServant servant, InventoryPlayer playerInventory, Container inventorySlotsIn) {
		super(servant, playerInventory, inventorySlotsIn);

		this.servant = servant;

		this.rand = new Random();
	}

	@Override
	public void initGui() {
		super.initGui();

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		//this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.renderHoveredToolTip(mouseX, mouseY);
		this.oldMouseX = mouseX;
		this.oldMouseY = mouseY;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.servant.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, 84, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, 64, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		this.renderCurrentSlot();

		this.servant.isGui = true;
		drawEntityOnScreen(i + 51, j + 75 - 16, 30, i + 51 - this.oldMouseX, j + 75 - 50 - this.oldMouseY, this.servant);
		this.servant.isGui = false;

		this.mc.getTextureManager().bindTexture(Gui.ICONS);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		renderArmor(guiLeft + 84, guiTop + 20);
		renderHealth(guiLeft + 84, guiTop + 77);
		renderAir(guiLeft + 84, guiTop + 56);
		//drawHeathArmor(0, 0);

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

	}

	protected void renderCurrentSlot() {

		int i = this.guiLeft;
		int j = this.guiTop;

		int i1 = 6 + 18 * (this.servant.inventory.currentItem % 9);
		int ij = 74 + 18 * (this.servant.inventory.currentItem / 9);

		this.drawTexturedModalRect(i + i1, j + ij, 176, 0, 20, 20);

	}

	protected void renderArmor(int x, int y) {
		int left = x;
		int top = y;

		int level = ForgeLSMHooks.getTotalArmorValue(this.servant);
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				drawTexturedModalRect(left, top, 34, 9, 9, 9);
			} else if (i == level) {
				drawTexturedModalRect(left, top, 25, 9, 9, 9);
			} else if (i > level) {
				drawTexturedModalRect(left, top, 16, 9, 9, 9);
			}
			left += 8;
		}
	}

	public void renderHealth(int width, int height) {

		EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
		int health = MathHelper.ceil(player.getHealth());
		boolean highlight = healthUpdateCounter > updateCounter && (healthUpdateCounter - updateCounter) / 3L % 2L == 1L;

		if (health < this.servantHealth && player.hurtResistantTime > 0) {
			this.lastSystemTime = Minecraft.getSystemTime();
			this.healthUpdateCounter = this.updateCounter + 20;
		} else if (health > this.servantHealth && player.hurtResistantTime > 0) {
			this.lastSystemTime = Minecraft.getSystemTime();
			this.healthUpdateCounter = this.updateCounter + 10;
		}

		if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
			this.servantHealth = health;
			this.lastServantHealth = health;
			this.lastSystemTime = Minecraft.getSystemTime();
		}

		this.servantHealth = health;
		int healthLast = this.lastServantHealth;

		IAttributeInstance attrMaxHealth = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		float healthMax = (float) attrMaxHealth.getAttributeValue();
		float absorb = MathHelper.ceil(player.getAbsorptionAmount());

		int healthRows = MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
		int rowHeight = Math.max(10 - (healthRows - 2), 3);

		this.rand.setSeed(updateCounter * 312871);

		int left_height = 39;

		int left = width;
		int top = height - left_height;
		left_height += (healthRows * rowHeight);
		if (rowHeight != 10) left_height += 10 - rowHeight;

		int regen = -1;
		if (player.isPotionActive(MobEffects.REGENERATION)) {
			regen = updateCounter % 25;
		}

		final int TOP = 9 * (mc.world.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
		final int BACKGROUND = (highlight ? 25 : 16);
		int MARGIN = 16;
		if (player.isPotionActive(MobEffects.POISON)) MARGIN += 36;
		else if (player.isPotionActive(MobEffects.WITHER)) MARGIN += 72;
		float absorbRemaining = absorb;

		for (int i = MathHelper.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
			//int b0 = (highlight ? 1 : 0);
			int row = MathHelper.ceil((i + 1) / 10.0F) - 1;
			int x = left + i % 10 * 8;
			int y = top - row * rowHeight;

			if (health <= 4) y += rand.nextInt(2);
			if (i == regen) y -= 2;

			drawTexturedModalRect(x, y, BACKGROUND, TOP, 9, 9);

			if (highlight) {
				if (i * 2 + 1 < healthLast) drawTexturedModalRect(x, y, MARGIN + 54, TOP, 9, 9); //6
				else if (i * 2 + 1 == healthLast) drawTexturedModalRect(x, y, MARGIN + 63, TOP, 9, 9); //7
			}

			if (absorbRemaining > 0.0F) {
				if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
					drawTexturedModalRect(x, y, MARGIN + 153, TOP, 9, 9); //17
					absorbRemaining -= 1.0F;
				} else {
					drawTexturedModalRect(x, y, MARGIN + 144, TOP, 9, 9); //16
					absorbRemaining -= 2.0F;
				}
			} else {
				if (i * 2 + 1 < health) drawTexturedModalRect(x, y, MARGIN + 36, TOP, 9, 9); //4
				else if (i * 2 + 1 == health) drawTexturedModalRect(x, y, MARGIN + 45, TOP, 9, 9); //5
			}
		}

	}

	protected void renderAir(int width, int height) {
		GlStateManager.enableBlend();
		int left = width + 81;
		int top = height;

		if (servant.isInsideOfMaterial(Material.WATER) || 0 == 0) {
			int air = servant.getAir();
			int full = MathHelper.ceil((air - 2) * 10.0D / 300.0D);
			int partial = MathHelper.ceil(air * 10.0D / 300.0D) - full;

			for (int i = 0; i < full + partial; ++i) {
				drawTexturedModalRect(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
			}
		}

	}

	/**
	 * サーヴァントを描画する
	 */
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate(posX, posY, 50.0F);
		GlStateManager.scale((-scale), scale, scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		ent.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
		ent.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
		ent.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		ent.prevRotationYawHead = f3;
		ent.rotationYawHead = f4;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

}
