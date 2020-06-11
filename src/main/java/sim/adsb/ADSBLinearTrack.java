package sim.adsb;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.model.Cities;
import sim.model.Point;
import sim.GeoOps;
import sim.Sink;

public class ADSBLinearTrack implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(ADSBCircleTrack.class);

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss.SSS")
			.withZone(ZoneId.systemDefault());

	private String hexIdent = "HEXxx";
	private String callsign = "CS0000";

	private double speed = 250.0; // in [m/s] = 900 [km/h] = 485 [kn]

	private List<Point> points = new ArrayList<>();

	private int position = 0;
	private double timeInterval = 1.0; // in [s]
	
	public ADSBLinearTrack(String hexIdent, String callsign, Entry<String, Point> city1, Entry<String, Point> city2) {
		while (city1.getValue().getLatitude() == city2.getValue().getLatitude() && city1.getValue().getLongitude() == city2.getValue().getLongitude()) {
			city2 = Cities.getRandom();
		}
		log.info("Created ADSB route from " + city1.getKey() + " to " + city2.getKey());
		init(hexIdent, callsign, city1.getValue().getLatitude(), city1.getValue().getLongitude(), city2.getValue().getLatitude(), city2.getValue().getLongitude());
	}

	public ADSBLinearTrack(String hexIdent, String callsign, double lat1, double lon1, double lat2, double lon2) {
		init(hexIdent, callsign, lat1, lon1, lat2, lon2);
	}
	
	private void init(String hexIdent, String callsign, double lat1, double lon1, double lat2, double lon2) {
		this.hexIdent = hexIdent;

		double distance = GeoOps.getDistance(lat1, lon1, lat2, lon2);
		distance = Math.abs(distance);
		double time = distance / speed;
		int nrOfGeneratedPoints = (int)(time / timeInterval);
		
		double stepLat = Math.abs((lat1 - lat2) / nrOfGeneratedPoints);
		double stepLon = Math.abs((lon1 - lon2) / nrOfGeneratedPoints);

		double factorLat = 1.0;
		double factorLon = 1.0;

		if (lat1 > lat2)
			factorLat = -factorLat;
		if (lon1 > lon2)
			factorLon = -factorLon;

		for (int i = 0; i < nrOfGeneratedPoints; i++) {
			points.add(new Point(lat1 + factorLat * i * stepLat, lon1 + factorLon * i * stepLon));
		}

		log.info("Created track for ADSB({}) with {} trackpoints.", hexIdent, nrOfGeneratedPoints);
	}

	@Override
	public void run() {
		while (true) {
			Point current = points.get(position);
			
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
			
			Sink.take(message1);
			Sink.take(message3);
			Sink.take(message4);
			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
