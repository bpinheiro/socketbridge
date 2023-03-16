package src.thread;

import java.io.IOException;
import java.net.Socket;

import src.common.Core;
import src.common.Logger;

public class ConnectionThread  implements Runnable {

	private Socket socket;
	private int port;
	private String hostIp;


	public ConnectionThread(Socket socket, String hostIp, int port) {
		this.socket = socket;
		this.port = port;
		this.hostIp = hostIp;
		Core.log(Logger.INFO,"[" + socket.getInetAddress().toString()  +   "] init connection");
	}

	
	@Override
	public void run() {
		boolean looping =true;
		Socket serverSocket=null;
		
		try {
			serverSocket = new Socket(hostIp, port);
		} catch (IOException e1) {
			looping = false;
		}
		
		while (looping) {
			try {

				if (socket.isClosed()) {
					Core.log(Logger.DETAIL, "[" + socket.getInetAddress().toString()  +   "] closed");
					looping = false;
					
				} else {
					
					//mandando para o servidor
					int txAv = socket.getInputStream().available();
					if (txAv > 0 ) {
						Core.log(Logger.DETAIL, "[" + socket.getInetAddress().toString()  +   "] tx " + txAv + " bytes");
						byte tx[] = new byte[txAv];
						socket.getInputStream().read(tx);
						serverSocket.getOutputStream().write(tx);
					}
					
					//recebendo dados do servidor
					int rxAv = serverSocket.getInputStream().available();
					if (rxAv > 0) {
						Core.log(Logger.DETAIL, "[" + socket.getInetAddress().toString()  +   "] rx " + rxAv + " bytes");
						byte rx[] =new byte[rxAv]; 
						serverSocket.getInputStream().read(rx);
						socket.getOutputStream().write(rx);
					}
					
					//Ja que nao veio nada da um delay
					if (txAv == 0 && rxAv == 0) {
						Thread.sleep(5);
					}
				}
				
				
			} catch (IOException ioe) {
				closeConnection();
				looping=false;
			} catch (Exception e) {
				Core.log(Logger.ERROR, "Error: " +  e.getMessage() + " Close connection");
				closeConnection();
				looping=false;
			}
		}
	}

	/**
	 * Fecha conexão
	 */
	public void closeConnection(){
		try {
			if(socket.getInputStream()!=null )socket.getInputStream().close();
			if(socket!=null)socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
