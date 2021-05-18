package util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EasyReader extends BufferedReader {

	public EasyReader(InputStream is) {
		super(new InputStreamReader(is));
	}
	
	public String readUntilEnd() throws IOException {
		StringBuffer sb = new StringBuffer();
		
		String line;
		
		while((line = this.readLine()) != null && line.length() != 0) {
			sb.append(line);
		}
		
		return sb.toString();
	}
	
}
