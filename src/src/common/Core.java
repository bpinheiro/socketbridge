package src.common;

import java.io.IOException;

import src.SocketBridgeConst;

public class Core {

	private static Config config = null;
	private static Logger logger = null;

	public Core(String configName) throws IOException  {
		config = new Config(configName, SocketBridgeConst.COMPLETE_VERSION);
		logger = new Logger(Logger.DETAIL | Logger.INFO | Logger.ERROR);
	} 

	public static Config getConfig() {
		return config;
	}

	public static void log(int level, String message){
		logger.log(level, message);
	}

	public static Logger getLogger() {
		return logger;
	}
}
