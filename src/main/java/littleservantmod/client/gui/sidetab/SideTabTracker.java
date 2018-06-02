package littleservantmod.client.gui.sidetab;

public class SideTabTracker {

	private static Class<? extends SideTabBase> openedRightTab;

	public static Class<? extends SideTabBase> getOpenedRightTab() {

		return openedRightTab;
	}

	public static void setOpenedRightTab(Class<? extends SideTabBase> tabClass) {

		openedRightTab = tabClass;
	}

}
