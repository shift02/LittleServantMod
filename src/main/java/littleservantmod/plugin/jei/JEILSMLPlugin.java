package littleservantmod.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEILSMLPlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {

		//何故か加速するので
		//SideTabBase.tabExpandSpeed = 4;

		registry.addAdvancedGuiHandlers(new LSMAdvancedGuiHandler());

	}

}
