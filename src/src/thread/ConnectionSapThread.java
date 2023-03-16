package src.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import src.common.Core;
import src.common.Logger;

public class ConnectionSapThread  implements Runnable {

	private Socket socket;


	public ConnectionSapThread(Socket socket) {
		this.socket = socket;
		Core.log(Logger.INFO,"[" + socket.getInetAddress().toString()  +   "] SAP init connection");
	}

	
	@Override
	public void run() {
		boolean looping =true;
		
		while (looping) {
			try {

				if (socket.isClosed()) {
					Core.log(Logger.DETAIL, "[" + socket.getInetAddress().toString()  +   "] SAP closed");
					looping = false;
					
				} else {
					
					//mandando para o servidor
					int txAv = socket.getInputStream().available();
					if (txAv > 0 ) {
						Core.log(Logger.DETAIL, "[" + socket.getInetAddress().toString()  +   "] SAP tx " + txAv + " bytes");
						byte tx[] =new byte[txAv]; 
						socket.getInputStream().read(tx);
						System.out.println(Arrays.toString(tx));
						socket.getOutputStream().write("RX: ".getBytes());
						socket.getOutputStream().write(tx);
					}
					Thread.sleep(10);
				}
				
				
			} catch (IOException ioe) {
				closeConnection();
				looping=false;
			} catch (Exception e) {
				Core.log(Logger.ERROR, "SAP Error: " +  e.getMessage() + " Close connection");
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
