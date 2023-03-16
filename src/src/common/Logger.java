package src.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	
	public static final int ERROR   = 0xFF;
	public static final int INFO    = 0x01;
	public static final int DETAIL  = 0x02;

	private final SimpleDateFormat sdfLog = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss] ");
	private int level;
	
	public Logger(int level) {
		super();
		this.level = level;
	}

	public void log(int lvl, String message) {
		if ( (lvl & this.level) > 0 ) {
			System.out.println(sdfLog.format(Calendar.getInstance().getTime()) + message);
		}
	} 
}
