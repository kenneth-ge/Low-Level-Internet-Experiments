package test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import main.CryptoHelper;

public class Test {

	static String password = "X@c<2c6C2L_tnT6N";
	static String  salt = "salt";
	
	static byte[] buffer = new byte[1024];
	
	public static void main(String[] args) throws Exception {
		CryptoHelper ch = new CryptoHelper(password, salt);
		
		var encrypted = ch.encrypt("X@c<2c6C2L_tnT6N\nKenny");
		
		DatagramSocket ds = new DatagramSocket();
		
		ds.send(new DatagramPacket(encrypted.getBytes(), encrypted.getBytes().length, InetAddress.getByName("127.0.0.1"), 3000));
		
		DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
		ds.receive(pack);
		
		encrypted = new String(buffer, 0, pack.getLength());
		
		System.out.println(encrypted);
		String message = ch.decrypt(encrypted);
		
		System.out.println(message);
		
		Thread.sleep(10000);
		
		ds.close();
	}
	
}
