package main;

public class Person {

	public String addr;
	public int port;
	public String name;
	public long addedTime;
	
	@Override
	public String toString() {
		return addr + "," + port + "," + name + "," + addedTime;
	}
	
}
