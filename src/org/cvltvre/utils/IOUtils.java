package org.cvltvre.utils;

import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

	public static String toString(InputStream stream) throws IOException{
		StringBuffer buffer = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = stream.read(b)) != -1;) {
			buffer.append(new String(b, 0, n));
		}
		return buffer.toString();
	}
	
}
