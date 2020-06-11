package sim.data.adsb;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.model.Point;
import sim.GeoOps;
import sim.SinkDispatcher;
import sim.config.Constants;

public class ADSBCircleTrack implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ADSBCircleTrack.class);
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss.SSS")
            .withZone(ZoneId.systemDefault());
	
	private String hexIdent = "abcdef";
	private String callsign = "0001SSE";
	
	private double speed = 250.0; // in [m/s] = 900 [km/h] = 485 [kn]
	
	private List<Point> points = new ArrayList<>();
	
	private int position = 0;
	private double timeInterval = 0.5;
	
	public ADSBCircleTrack(String hexIdent, String callsign, double centerLat, double centerLon, double radiusInDegree) {	
		this.hexIdent = hexIdent;
		this.callsign = callsign;
		
		double radius = Math.abs(GeoOps.getDistance(centerLat, centerLon, centerLat, centerLon + radiusInDegree));
		double perimeter = 2 * Math.PI * radius;
		double time  = perimeter / speed;
		int nrOfGeneratedPoints = (int)(time / timeInterval);
		
		for (int i = 0; i < nrOfGeneratedPoints; i++) {
		    double angle = Math.toRadians(((double) i / (double) nrOfGeneratedPoints) * 360.0);
		    points.add(new Point(centerLat + Math.cos(angle) * radiusInDegree, centerLon + Math.sin(angle) * radiusInDegree));
		}
		
		log.info("Created track for {} with wait Time of {} s.", hexIdent, timeInterval);
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
			message4 = message4.replace("${speed}", "485");
			message4 = message4.replace("${track}", String.valueOf(GeoOps.getBearing(current.getLatitude(), current.getLongitude(), points.get(position).getLatitude(), points.get(position).getLongitude())));
			
			SinkDispatcher.take(Constants.sinkAdsb, message1);
			SinkDispatcher.take(Constants.sinkAdsb, message3);
			SinkDispatcher.take(Constants.sinkAdsb, message4);
			try { Thread.sleep((long)(timeInterval * 1000L)); }
			catch (InterruptedException e) {}
		}
		
	}

}
