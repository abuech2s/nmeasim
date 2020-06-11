package sim.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Cities {

	private static Map<String, Point> cities = null;
	private static final Logger log = LoggerFactory.getLogger(Cities.class);

	private static InputStream getFileFromResources(String fileName) {
		ClassLoader classLoader = Cities.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			return stream;
		}
	}

	private static void init() {
		cities = new HashMap<>();

		InputStream stream = getFileFromResources("cities.csv");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				String[] elem = line.split(",");
				double lat = Double.parseDouble(elem[1]);
				double lon = Double.parseDouble(elem[2]);
				cities.put(elem[0], new Point(lat, lon));
			}
			br.close();
		} catch (IOException e) {
			log.warn("Exception while loding cities {}", e);
		}

		log.debug("Created {} cities.", cities.size());
	}

	public static Point get(String city) {
		if (cities == null)
			init();
		city = city.toLowerCase().trim();
		return cities.get(city);
	}

	@SuppressWarnings("unchecked")
	public static Entry<String, Point> getRandom() {
		if (cities == null)
			init();
		Random rand = new Random();
		Object[] values = cities.entrySet().toArray();
		Object o = values[rand.nextInt(values.length)];
		return (Entry<String, Point>) o;
	}

}
