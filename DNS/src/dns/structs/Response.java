package dns.structs;

public class Response {

	public Host dnsServer;
	public Host ownIP;
	
	public Response(Host ownIP, Host dnsServer) {
		this.ownIP = ownIP;
		this.dnsServer = dnsServer;
	}
	
}
