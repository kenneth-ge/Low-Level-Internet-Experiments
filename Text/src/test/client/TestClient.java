package test.client;

import client.TextClientDNS;
import util.SimpleErrorHandler;

public class TestClient {

	public static void main(String[] args) {
		TextClientDNS client = new TextClientDNS(new SimpleErrorHandler());
		
		System.out.println(client.request("tell me a fun fact!", "awesomeserver"));
	}
	
}
