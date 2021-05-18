package test;

import dns.DNSClient;
import util.SimpleErrorHandler;

public class ClientTest {

	public static void main(String[] args) {
		DNSClient client = new DNSClient(new SimpleErrorHandler());
		
		//var x = client.discover();
		//System.out.println("DNS Server " + x.dnsServer);
		//System.out.println("IP " + x.ownIP);
		//client.register("internetservice", 5000);
		System.out.println(client.get("awesome server"));
	}
}
