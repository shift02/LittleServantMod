package littleservantmod.packet;

import littleservantmod.LittleServantMod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class LSMPacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
			.newSimpleChannel(LittleServantMod.MOD_ID);

	public static void init(FMLPreInitializationEvent event) {
		/*
		 * Messageクラスの登録。 第一引数：IMessageHandlerクラス 第二引数：送るMessageクラス
		 * 第三引数：登録番号。255個まで 第四引数：ClientとServerのどちらに送るか。送り先
		 */
		INSTANCE.registerMessage(OpenGuiIdMessageHandler.class, MessageOpenGuiId.class, 0, Side.SERVER);

		INSTANCE.registerMessage(ChangeProfessionMessageHandler.class, MessageChangeProfession.class, 1, Side.SERVER);

		INSTANCE.registerMessage(ChangeModeMessageHandler.class, MessageChangeMode.class, 2, Side.SERVER);

		INSTANCE.registerMessage(ChangeBehaviorMessageHandler.class, MessageChangeBehavior.class, 3, Side.SERVER);

	}

}