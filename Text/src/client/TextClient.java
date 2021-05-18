package client;

import java.io.IOException;
import java.net.Socket;

import util.ErrorHandler;
import util.Location;
import util.ThrowsError;
import util.io.EasyReader;
import util.io.EasyWriter;

public class TextClient extends ThrowsError {
	
	public TextClient(ErrorHandler eh) {
		super(eh);
	}

	/** returns null if an error occurred */
	public String request(String req, Location l) {
		try {
			Socket socket = new Socket(l.ip, l.port);
			
			EasyWriter out = new EasyWriter(socket.getOutputStream());
			out.write(req + "\n");
			out.flush();
			
			EasyReader in = new EasyReader(socket.getInputStream());
			String ans = in.readUntilEnd();
			
			in.close(); out.close();
			
			socket.close();
			return ans;
		} catch (IOException e) {
			eh.handle(e, -1);
		}
		
		return null;
	}
	
}
