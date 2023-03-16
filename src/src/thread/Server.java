package src.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import src.common.Config;
import src.common.Core;
import src.common.Logger;

public class Server extends Thread {

	private int port;
	private Socket socket;
	private ServerSocket server;
	private String serverIp;
	private int serverPort;

	public Server() {
		this.port       = Core.getConfig().getIntConfig(Config.APP_PORT);
		this.serverIp   = Core.getConfig().getStringConfig(Config.SERVER_IP);
		this.serverPort = Core.getConfig().getIntConfig(Config.SERVER_PORT);
		Core.log(Logger.INFO,"Listen port: " + port);
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (server == null ) {
					server = new ServerSocket(port);
				}
				socket = server.accept();
				ConnectionThread conn= new ConnectionThread(this.socket, this.serverIp, this.serverPort);
				Thread t = new Thread(conn);
				t.start();
			} catch (Exception ex) {
			    if (socket != null) {
					try { socket.close();} catch (IOException e1) {}
				}
			    if (server != null) {
			    	try {
						server.close();
					} catch (IOException e) {}
			    }
			}
		}
	}
}