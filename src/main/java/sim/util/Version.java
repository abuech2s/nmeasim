package sim.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Constants;

public class Version {
	
	private static final Logger log = LoggerFactory.getLogger(Version.class);

	public static String get(String category) {
		InputStream stream = StreamUtils.getFileFromResources(Constants.FILE_VERSION);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				String[] elem = line.split(" : ");
				if (elem[0].strip().equalsIgnoreCase(category)) {
					return elem[1].strip();
				}
			}
			br.close();
		} catch (IOException e) {
			log.warn("Exception while reading version information {}", e);
		}
		return "-";
	}
	
}
