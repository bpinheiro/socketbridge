package src;

import java.io.IOException;

import src.common.Core;
import src.common.Logger;
import src.thread.Server;

public class SocketBridge {

	public static void main(String[] args) {
		try {
			new Core("socketbridge.ini");
			Core.log(Logger.INFO, "=========================================");
			Core.log(Logger.INFO, SocketBridgeConst.COMPLETE_VERSION);
			Core.log(Logger.INFO, "=========================================");
			Core.log(Logger.INFO, "Parametros: ");
			Core.log(Logger.INFO, Core.getConfig().showConfigurations());
			//new ServerSap();
			new Server();

		} catch (IOException e) {
			e.printStackTrace();
			if (Core.getLogger() != null) {
				Core.log(Logger.ERROR, "Exception: " + e.getMessage());
			} else {
				e.printStackTrace();
			}
		}
	}
}
