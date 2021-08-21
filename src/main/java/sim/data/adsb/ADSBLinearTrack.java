package sim.data.adsb;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.model.GeoOps;
import sim.model.GeoCoordinate;
import sim.model.tracks.Track;
import sim.config.Config;
import sim.data.adsb.data.Cities;

public class ADSBLinearTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(ADSBLinearTrack.class);

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss.SSS")
			.withZone(ZoneId.of("UTC"));

	private String hexIdent = "HEXxx";
	private String callsign = "CS0000";

	private int position = 0;
	
	public ADSBLinearTrack(Config config, String hexIdent, String callsign) {
		super(config, 250.0, 3);
		this.hexIdent = hexIdent;
		this.callsign = callsign;
		init();
	}
	
	private void init() {
		Entry<String, GeoCoordinate> city1 = Cities.getRandom();
		Entry<String, GeoCoordinate> city2 = Cities.getRandom();
		while (city1.getValue().getLatitude() == city2.getValue().getLatitude() && city1.getValue().getLongitude() == city2.getValue().getLongitude()) {
			city2 = Cities.getRandom();
		}
		
		List<GeoCoordinate> routePoints = new ArrayList<>();
		routePoints.add(new GeoCoordinate(city1.getValue().getLatitude(), city1.getValue().getLongitude()));
		routePoints.add(new GeoCoordinate(city2.getValue().getLatitude(), city2.getValue().getLongitude()));
		
		createGeoCoordinates(routePoints);
		log.debug("Created track {} for ADSB with {} trackpoints from {} to {}.", hexIdent, points.size(), city1.getKey(), city2.getKey());
	}

	@Override
	public void run() {
		while (!kill) {
			GeoCoordinate current = points.get(position);
			
			position++;
			position = position % points.size();
			
			
			String message1 = ADSBMessages.MSG_1;
			message1 = message1.replace("${hexident}", hexIdent);
			message1 = message1.replace("${callsign}", callsign);
			message1 = message1.replace("${time}", dateTimeFormatter.format(new Date().toInstant()));
			
			String message3 = ADSBMessages.MSG_3;
			message3 = message3.replace("${hexident}", hexIdent);
			message3 = message3.replace("${lat}", String.valueOf(current.getLatitude()));
			message3 = message3.replace("${lon}", String.valueOf(current.getLongitude()));
			message3 = message3.replace("${time}", dateTimeFormatter.format(new Date().toInstant()));
			
			String message4 = ADSBMessages.MSG_4;
			message4 = message4.replace("${hexident}", hexIdent);
			message4 = message4.replace("${time}", dateTimeFormatter.format(new Date().toInstant()));
			message4 = message4.replace("${speed}", "485"); // 485 kn
			message4 = message4.replace("${track}", String.valueOf(GeoOps.getBearing(current.getLatitude(), current.getLongitude(), points.get(position).getLatitude(), points.get(position).getLongitude())));
			
			publish(message1);
			publish(message3);
			publish(message4);
			
			try {
				Thread.sleep(timeInterval * 1_000);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
		}
	}

}
