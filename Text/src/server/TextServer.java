package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import util.ErrorHandler;

public abstract class TextServer {

	int port;
	ErrorHandler eh;
	
	/** You might also want to register this server in the DNS */
	public TextServer(int port, ErrorHandler eh) {
		this.port = port;
		this.eh = eh;
	}
	
	public void start() {
		Socket sock = null;
		
		try (ServerSocket ss = new ServerSocket(port)) {
			while(true) {
				try {
					sock = ss.accept();
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					
					String request = reader.readLine();
					
					String response = handle(request);
					
					BufferedWriter output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
					output.append(response + "\n");
					output.flush();
					
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(IOException e) {
			eh.handle(e, -1);
		}
	}
	
	public abstract String handle(String request);
	
}
