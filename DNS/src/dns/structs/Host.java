package dns.structs;

import java.net.InetAddress;

public class Host {
	
	public InetAddress addr;
	public int port;
	
	public Host(InetAddress addr, int port) {
		this.addr = addr;
		this.port = port;
	}
	
	public Host(String s) throws Exception {
		var arr = s.split(",");
		
		addr = InetAddress.getByName(arr[0].substring(1, arr[0].length()));
		port = Integer.parseInt(arr[1]);
	}
	
	@Override
	public String toString() {
		return addr.toString() + "," + port;
	}
	
}
