package littleservantmod.client.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import littleservantmod.api.IServant;
import littleservantmod.api.profession.IProfession;
import littleservantmod.client.gui.inventory.GuiServantBase;
import littleservantmod.client.gui.inventory.GuiServantProfessionSelectProfession;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;

public class ElementChangeIcon extends ElementBase {

	protected IProfession profession;

	protected IServant servant;

	protected ElementChangeIcon current;

	protected GuiServantProfessionSelectProfession gui;

	public ElementChangeIcon(GuiContainer gui, int posX, int posY, IProfession profession, IServant servant, ElementChangeIcon current) {
		super(gui, posX, posY);
		this.sizeX = 16;
		this.sizeY = 16;

		this.gui = (GuiServantProfessionSelectProfession) gui;

		this.profession = profession;

		this.servant = servant;

		this.current = current;

	}

	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		if (current == null) return false;

		current.setProfession(this.profession);
		return true;

	}

	@Override
	public void addTooltip(List<String> list) {
		list.add("sssss");
	}

	public String getProfessionDisplayName() {
		return profession.getProfessionDisplayName(servant);
	}

	@Override
	public void draw() {
		// TODO 自動生成されたメソッド・スタブ

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		//gui.zLevel = 100.0F;
		((GuiServantBase) gui).setZLevel(100.0f);
		((GuiServantBase) gui).setItemZLevel(100.0f);

		//GlStateManager.disableLighting();
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		gui.drawTexturedModalRect(this.posX, this.posY, this.profession.getIcon(servant), 16, 16);
		//GlStateManager.enableLighting();

		((GuiServantBase) gui).setItemZLevel(0.0f);
		((GuiServantBase) gui).setZLevel(0);
		//this.zLevel = 0.0F;

		GL11.glDisable(GL11.GL_DEPTH_TEST);

	}

	public void setProfession(IProfession profession) {
		this.profession = profession;
	}

	public IProfession getProfession() {
		return profession;
	}

}
