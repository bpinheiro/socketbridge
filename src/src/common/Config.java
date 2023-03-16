package src.common;

import java.io.File;
import java.io.IOException;

public class Config extends AbstractConfigurations {

	public static final String SERVER_PORT				= "SOCKET_PORT";
	public static final String APP_PORT					= "APP_PORT";
	public static final String SERVER_IP				= "SERVER_IP";

	
	public Config(String fileName, String description) throws IOException {
		super(new File("."),fileName, description);
	}
	
	@Override
	public void fillMyConfigurations() {
		initConfiguration(SERVER_PORT,"1433"				,"Server port");
		initConfiguration(APP_PORT   ,"1433"				,"Application port");
		initConfiguration(SERVER_IP  ,"193.123.119.17"	    ,"Server ip");
	}
}