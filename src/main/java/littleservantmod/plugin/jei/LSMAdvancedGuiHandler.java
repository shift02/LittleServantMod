package littleservantmod.plugin.jei;

import java.awt.Rectangle;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import littleservantmod.client.gui.inventory.GuiSideTabContainer;
import littleservantmod.client.gui.sidetab.SideTabBase;
import mezz.jei.api.gui.IAdvancedGuiHandler;

public class LSMAdvancedGuiHandler implements IAdvancedGuiHandler<GuiSideTabContainer> {

	@Override
	public Class getGuiContainerClass() {
		return GuiSideTabContainer.class;
	}

	@Override
	@Nullable
	public List<Rectangle> getGuiExtraAreas(GuiSideTabContainer guiContainer) {

		List<Rectangle> list = Lists.newArrayList();

		for (SideTabBase tab : guiContainer.tabs) {
			list.add(new Rectangle(tab.getPosX(), tab.getPosY(), tab.currentWidth, tab.currentHeight));
		}

		return list;
	}

}
