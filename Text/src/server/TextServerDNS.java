package server;

import dns.DNSClient;
import util.ErrorHandler;

/** Text server with DNS support */
public abstract class TextServerDNS extends TextServer {

	public TextServerDNS(int port, ErrorHandler eh, String name) {
		super(port, eh);
		
		DNSClient client = new DNSClient(eh);
		client.register(name, port);
	}

}
