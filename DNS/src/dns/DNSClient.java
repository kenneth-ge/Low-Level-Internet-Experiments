package dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import dns.exceptions.CantFind;
import dns.exceptions.NullException;
import dns.exceptions.RegisterFailed;
import dns.structs.Host;
import dns.structs.Packet;
import dns.structs.Response;
import util.ErrorHandler;

public class DNSClient {

	public static final int DNS_PORT = 3000;
	
	DatagramSocket sock;
	byte[] buf;
	
	ErrorHandler eh;
	
	Host dnsServer;
	
	public DNSClient(ErrorHandler eh) {
		this.eh = eh;
		
		try {
			sock = new DatagramSocket();
			sock.setSoTimeout(3 * 1000);
		} catch (SocketException e) {
			eh.handle(e, -1);
		}
	}
	
	/** Gets the IP address of the DNS server as well as your IP address and port # */
	public Response discover() {
		String message = "discover";
		
		send(message, "255.255.255.255", DNS_PORT);
		
		var pack = receive();
		
		if(pack == null)
			return null;
		
		dnsServer = new Host(pack.packet.getAddress(), pack.packet.getPort());
		
		try {
			return new Response(new Host(pack.s), dnsServer);
		} catch (Exception e) {
			eh.handle(e, -1);
			return null;
		}
	}
	
	public void register(String name, int port) {
		String message = "register\n" + port + "\n" + name;
		
		send(message);
		
		var pack = receive();
		
		if(pack == null) {
			eh.handle(new NullException(), -1);
			return;
		}
		
		if(!pack.s.toLowerCase().contains("succ")) {
			eh.handle(new RegisterFailed(pack.s), -1);
		}
	}
	
	public Host get(String name) {
		String message = "req\n" + name;
		
		send(message);
		
		var pack = receive();
		
		if(pack == null) {
			eh.handle(new NullException(), -1);
			return null;
		}
		
		try {
			return new Host(pack.s);
		} catch (Exception e) {
			eh.handle(new CantFind(pack.s), -1);
			return null;
		}
	}
	
	private Packet receive() {
		var buff = new byte[512];
		var packet = new DatagramPacket(buff, buff.length);
		
		try {
			sock.receive(packet);
		
			String res = new String(buff, 0, packet.getLength());
			
			var pack = new Packet();
			pack.packet = packet;
			pack.s = res;
			
			return pack;
		} catch (Exception e) {
			eh.handle(e, -1);
			return null;
		}
	}
	
	/** Send to DNS server */
	private void send(String message) {
		if(dnsServer == null) {
			discover();
		}
		
		send(message, dnsServer.addr, dnsServer.port);
	}
	
	private void send(String message, String ip, int port) {
		try {
			send(message, InetAddress.getByName(ip), port);
		} catch (IOException e) {
			eh.handle(e, -1);
		}
	}
	
	private void send(String message, InetAddress addr, int port) {
		var buff = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buff, buff.length, addr, port);
		
		try {
			sock.send(packet);
		} catch (IOException e) {
			eh.handle(e, -1);
		}
	}
	
}
