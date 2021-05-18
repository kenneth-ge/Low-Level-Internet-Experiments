package main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class STUN {

	final Person[] people = new Person[5];
	
	static final long DELETE_TIME = 20 * 60 * 1000;
	
	final boolean debug = true;
	
	byte[] buffer = new byte[256];
	Person[] entries = new Person[5];
	
	final String password = "X@c<2c6C2L_tnT6N";
	
	CryptoHelper ch;
	
	public STUN() {
		DatagramSocket sock = null;
		
		try {
			ch = new CryptoHelper(password, "salt");
			sock = new DatagramSocket(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}

		while(true) {
			try {
				var packet = new DatagramPacket(buffer, buffer.length);
				sock.receive(packet);
				
				String cypherText = new String(buffer, 0, packet.getLength());
				System.out.println(cypherText);
				
				String[] lines = ch.decrypt(cypherText).split("\n");
				
				if(lines[0].equals(password)) {
					//replace if already present; otherwise find earliest slot or any unfilled slot
					String ipAddr = packet.getAddress().getHostAddress();
					int idx = 0;
					for(int i = 0; i < people.length; i++) {
						if(people[i] == null) {
							idx = i;
							continue;
						}
						if(people[i].addr.equals(ipAddr)) {
							idx = i;
							break;
						}else if(people[i] == null) {
							idx = i;
							break;
						}else if(people[idx] != null && people[i].addedTime < people[idx].addedTime) {
							idx = i;
						}
					}
					
					//add to ip address list
					people[idx] = new Person();
					people[idx].addr = packet.getAddress().getHostAddress();
					people[idx].port = packet.getPort();
					people[idx].addedTime = System.currentTimeMillis();
					people[idx].name = lines[1];
					
					//generate packet to send out
					StringBuffer sb = new StringBuffer();
					
					long currTime = System.currentTimeMillis();
					for(int i = 0; i < people.length; i++) {
						if(people[i] == null)
							continue;
						
						if(currTime - people[i].addedTime >= DELETE_TIME) {
							people[i] = null;
							continue;
						}
						
						sb.append(people[i].toString());
						sb.append('\n');
					}
					
					var message = ch.encrypt(sb.toString()).getBytes();
					
					System.out.println(message.length);
					sock.send(new DatagramPacket(message, message.length, packet.getAddress(), packet.getPort()));
				}else{
					sock.send(emptyPacket(packet.getAddress(), packet.getPort()));
					continue;
				}
			}catch(Exception e) {
				//debug purposes
				if(debug)
					e.printStackTrace();
			}
		}
	}
	
	public DatagramPacket emptyPacket(InetAddress addr, int port) {
		return new DatagramPacket(new byte[] {0}, 1, addr, port);
	}

	public static void main(String[] args) throws Exception {
		new STUN();
	}

}
