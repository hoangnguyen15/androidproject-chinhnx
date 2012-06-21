package chau.nguyen.calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtils {
	public static String readAllText(InputStream inputStream) {
		return readAllText(inputStream, "UTF-8");
	}
	public static String readAllText(InputStream inputStream, String encoding) {
		StringBuffer sb = new StringBuffer();
		try {		
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
			String line = null;
			while ((line = reader.readLine()) != null) {				
				sb.append(line).append("\n");
			}
			inputStream.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		return sb.toString();
	}
}
