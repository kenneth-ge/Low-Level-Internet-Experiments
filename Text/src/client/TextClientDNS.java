package client;

import dns.DNSClient;
import dns.structs.Host;
import util.ErrorHandler;
import util.Location;

public class TextClientDNS extends TextClient {

	private DNSClient client;
	
	public TextClientDNS(ErrorHandler eh) {
		super(eh);
		
		client = new DNSClient(eh);
	}

	public String request(String request, String locationName) {
		Host location = client.get(locationName);
		
		return request(request, new Location(location.addr, location.port));
	}
	
}
