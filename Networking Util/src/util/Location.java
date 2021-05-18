package util;

import java.net.InetAddress;

public class Location {

	public InetAddress ip;
	public int port;
	
	public Location(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
}
