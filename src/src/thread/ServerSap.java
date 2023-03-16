package src.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import src.common.Config;
import src.common.Core;
import src.common.Logger;

public class ServerSap extends Thread {

	private int port;
	private Socket socket;
	private ServerSocket server;

	public ServerSap() {
		this.port       = Core.getConfig().getIntConfig(Config.SERVER_PORT);
		Core.log(Logger.INFO,"SAP Listen port: " + port);
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
				ConnectionSapThread conn= new ConnectionSapThread(this.socket);
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