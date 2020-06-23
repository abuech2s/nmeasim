package sim.data.gps;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Constants;
import sim.model.GeoOps;
import sim.model.Point;
import sim.model.sinks.SinkDispatcher;
import sim.model.tracks.Track;

public class GPSTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(GPSTrack.class);

	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss").withZone(ZoneId.systemDefault());
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyy").withZone(ZoneId.systemDefault());
	
	private double speed = 100; // 13 in [m/s] = 46 [km/h] = 25 [kn]

	private List<Point> points = new ArrayList<>();

	private int position = 0;
	private double timeInterval = 5.0; // in [s]

	public GPSTrack(List<Point> route) {
		init(route);
	}
	
	private void init(List<Point> route) {
		
		for (int p = 0; p < route.size()-1; p++) {
			double lat1 = route.get(p).getLatitude();
			double lon1 = route.get(p).getLongitude();
			double lat2 = route.get(p+1).getLatitude();
			double lon2 = route.get(p+1).getLongitude();

			double distance = Math.abs(GeoOps.getDistance(route.get(p).getLatitude(), route.get(p).getLongitude(),
					route.get(p+1).getLatitude(), route.get(p+1).getLongitude()));
			double time = distance / speed; // in [s]
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
		}
		
		log.info("Created track for GPS with {} trackpoints.", points.size());
	}

	@Override
	public void run() {
		while (!kill) {
			Point current = points.get(position);
			
			position++;
			position = position % points.size();
				
			String message1 = GPSMessages.MSG_GPGGA;
			message1 = message1.replace("${time}", timeFormatter.format(new Date().toInstant()));
			message1 = message1.replace("${lat}", GeoOps.GeoDecToDegMin(current.getLatitude(), 4, 4) );
			if (current.getLatitude() < 0) message1 = message1.replace("${latNS}", "S");
			else message1 = message1.replace("${latNS}", "N");
							
			message1 = message1.replace("${lon}", GeoOps.GeoDecToDegMin(current.getLongitude(), 5, 4));
			if (current.getLongitude() < 0) message1 = message1.replace("${lonWE}", "W");
			else message1 = message1.replace("${lonWE}", "E");
			
			String message2 = GPSMessages.MSG_GPRMC;
			message2 = message2.replace("${lat}", GeoOps.GeoDecToDegMin(current.getLatitude(), 4, 2));
			if (current.getLatitude() < 0) message2 = message2.replace("${latNS}", "S");
			else message2 = message2.replace("${latNS}", "N");
			message2 = message2.replace("${lon}", GeoOps.GeoDecToDegMin(current.getLongitude(), 5, 2));
			if (current.getLongitude() < 0) message2 = message2.replace("${lonWE}", "W");
			else message2 = message2.replace("${lonWE}", "E");
			message2 = message2.replace("${time}", timeFormatter.format(new Date().toInstant()));
			message2 = message2.replace("${date}", dateFormatter.format(new Date().toInstant()));
			
			SinkDispatcher.take(Constants.tokenGps, message1);
			SinkDispatcher.take(Constants.tokenGps, message2);
			
			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}

		}
	}
}
