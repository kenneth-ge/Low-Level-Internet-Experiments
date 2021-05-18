package dns;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import dns.structs.Host;

public class DNSServer {

	public static DatagramSocket socket;
	public static byte[] buffer = new byte[512];
	
	public static Map<String, Host> registry;
	
	public static void main(String[] args) {
		//initialize stuff
		try {
			socket = new DatagramSocket(3000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		
		registry = new HashMap<>();
		
		//handle multiple requests
		while(true) {
			handle();
		}
	}
	
	public static void handle() {
		var packet = new DatagramPacket(buffer, buffer.length);
		
		try {
			socket.receive(packet);
		
			var addr = packet.getAddress();
			int port = packet.getPort();
			
			String s = new String(buffer, 0, packet.getLength());
			var lines = s.split("\n");
			
			String sendData = null;
			
			switch(lines[0]) {
			case "req":
				String name = lines[1];
				
				if(!registry.containsKey(name))
					sendData = "error, couldn't find that";
				else
					sendData = registry.get(name).toString();
				break;
			case "discover":
				sendData = new Host(addr, port).toString();
				
				break;
			case "register":
				Host h = null;
				
				try {
					h = new Host(addr, Integer.parseInt(lines[1]));
				}catch(Exception e) {
					e.printStackTrace();
					sendData = "error registering";
					break;
				}
				
				registry.put(lines[2], h);
				sendData = "success!";
				
				break;
			default:
				sendData = "Unknown. Please try again";
				break;
			}
			
			byte[] res = sendData.getBytes();
			packet = new DatagramPacket(res, res.length, addr, port);
			socket.send(packet);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
}
