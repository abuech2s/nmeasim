package sim.util;

import java.io.InputStream;

import sim.data.adsb.data.Cities;

public class StreamUtils {

	public static InputStream getFileFromResources(String fileName) {
		ClassLoader classLoader = Cities.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null) {
			throw new IllegalArgumentException("file: " + fileName + " not found!");
		} else {
			return stream;
		}
	}
	
}
