package test.server;

import server.TextServerDNS;
import util.ErrorHandler;
import util.SimpleErrorHandler;

public class TestServer extends TextServerDNS {

	public TestServer(int port, ErrorHandler eh, String name) {
		super(port, eh, name);
	}

	@Override
	public String handle(String request) {
		if(request.contains("fun"))
			return "Kevin is fun!";
			
		return "Kevin is awesome!";
	}

	public static void main(String[] args) {
		new TestServer(6000, new SimpleErrorHandler(), "awesome server").start();
	}
	
}
