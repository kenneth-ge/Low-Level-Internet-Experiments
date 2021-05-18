package util.io;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class EasyWriter extends BufferedWriter {

	public EasyWriter(OutputStream out) {
		super(new OutputStreamWriter(out));
	}

}
