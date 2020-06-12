package sim.data.radar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Constants;
import sim.data.gps.GPSMessages;
import sim.model.GeoOps;
import sim.model.Point;
import sim.model.sinks.SinkDispatcher;
import sim.model.tracks.Track;

public class RadarTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(RadarTrack.class);

	DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
	
	private DateTimeFormatter dateFormatterGps = DateTimeFormatter.ofPattern("yyMMdd").withZone(ZoneId.systemDefault());
	private DateTimeFormatter timeFormatterGps = DateTimeFormatter.ofPattern("HHmmss").withZone(ZoneId.systemDefault());
	
	private DateTimeFormatter dateTimeFormatterRadar = DateTimeFormatter.ofPattern("HHmmss.SS").withZone(ZoneId.systemDefault());
	
	private DecimalFormat df2 = new DecimalFormat("00.0");
	private DecimalFormat df3 = new DecimalFormat("000.0");
	
	private DecimalFormat lonFormat = new DecimalFormat("00000.00", symbols);
	private DecimalFormat latFormat = new DecimalFormat("0000.00", symbols);
	
	private DecimalFormat lonFormatGGA = new DecimalFormat("00000.0000", symbols);
	private DecimalFormat latFormatGGA = new DecimalFormat("0000.0000", symbols);
	
	private static final double maxRadarRadius = 30_000.0;

	private double speed = 100; // 13 in [m/s] = 46 [km/h] = 25 [kn]

	private List<Point> points = new ArrayList<>();

	private int position = 0;
	private double timeInterval = 5.0; // in [s]
	
	
	private int radarTrackCounter = 0;
	private String currentRadarTrackId = null;

	public RadarTrack(List<Point> route) {
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
			message1 = message1.replace("${time}", timeFormatterGps.format(new Date().toInstant()));
			message1 = message1.replace("${lat}", latFormatGGA.format(Math.abs(current.getLatitude()*100.0)));
			if (current.getLatitude() < 0) message1 = message1.replace("${latNS}", "S");
			else message1 = message1.replace("${latNS}", "N");
							
			message1 = message1.replace("${lon}", lonFormatGGA.format(Math.abs(current.getLongitude()*100.0)));
			if (current.getLongitude() < 0) message1 = message1.replace("${lonWE}", "W");
			else message1 = message1.replace("${lonWE}", "E");
			
			String message2 = GPSMessages.MSG_GPRMC;
			message2 = message2.replace("${lat}", latFormat.format(Math.abs(current.getLatitude()*100.0)) );
			if (current.getLatitude() < 0) message2 = message2.replace("${latNS}", "S");
			else message2 = message2.replace("${latNS}", "N");
			message2 = message2.replace("${lon}", lonFormat.format(Math.abs(current.getLongitude()*100.0))  );
			if (current.getLongitude() < 0) message2 = message2.replace("${lonWE}", "W");
			else message2 = message2.replace("${lonWE}", "E");
			message2 = message2.replace("${time}", timeFormatterGps.format(new Date().toInstant()));
			message2 = message2.replace("${date}", dateFormatterGps.format(new Date().toInstant()));
			
			message1 = message1 + GeoOps.calcCheckSum(message1);
			message2 = message2 + GeoOps.calcCheckSum(message2);
			
			SinkDispatcher.take(Constants.sinkGps, message1);
			SinkDispatcher.take(Constants.sinkGps, message2);
			
			//Based on GPS position, we check furthermore, if we can find a track plot
			
			Point radarPlot = isInRadiusOfRadarPlot(current.getLatitude(), current.getLongitude());
			
			if (radarPlot != null) {
				if (currentRadarTrackId == null) radarTrackCounter++;
				radarTrackCounter = radarTrackCounter % 100;
				currentRadarTrackId = "0" + radarTrackCounter;
				String radarMsg = RadarMessage.MSG_TTM;
				
				double dist = GeoOps.getDistance(current.getLatitude(), current.getLongitude(), radarPlot.getLatitude(), radarPlot.getLongitude());
				double bearing = GeoOps.getBearing(current.getLatitude(), current.getLongitude(), radarPlot.getLatitude(), radarPlot.getLongitude());
				
				dist = dist * 0.000539957; // [m] in [NM]
				bearing = (540.0 - bearing) % 360; // Calculate based on True North
				
				radarMsg = radarMsg.replace("${trackid}", currentRadarTrackId);
				radarMsg = radarMsg.replace("${dist}", df2.format(dist).replace(",", "."));
				radarMsg = radarMsg.replace("${bearing}", df3.format(bearing).replace(",", "."));
				radarMsg = radarMsg.replace("${time}", dateTimeFormatterRadar.format(new Date().toInstant()));
				radarMsg = radarMsg.replace("${name}", "TRK"+currentRadarTrackId);
				
				radarMsg = radarMsg + GeoOps.calcCheckSum(radarMsg);

				SinkDispatcher.take(Constants.sinkRadar, radarMsg);
			} else {
				currentRadarTrackId = null;
			}

			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}

		}
	}
	
	private Point isInRadiusOfRadarPlot(double lat, double lon) {
		for (Point radarPlot : RadarPlots.get()) {
			double dist = GeoOps.getDistance(lat,  lon, radarPlot.getLatitude(),  radarPlot.getLongitude());
			if (dist <= RadarTrack.maxRadarRadius) {
				return radarPlot;
			}
		}
		return null;
	}
}
