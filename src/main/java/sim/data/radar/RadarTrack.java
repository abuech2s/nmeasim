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
import sim.model.GeoCoordinate;
import sim.model.sinks.SinkDispatcher;
import sim.model.tracks.Track;

public class RadarTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(RadarTrack.class);

	DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
	
	private DateTimeFormatter dateFormatterGps = DateTimeFormatter.ofPattern("ddMMyy").withZone(ZoneId.systemDefault());
	private DateTimeFormatter timeFormatterGps = DateTimeFormatter.ofPattern("HHmmss").withZone(ZoneId.systemDefault());
	private DateTimeFormatter dateTimeFormatterRadar = DateTimeFormatter.ofPattern("HHmmss.SS").withZone(ZoneId.systemDefault());
	
	private DecimalFormat df2 = new DecimalFormat("00.0");
	private DecimalFormat df3 = new DecimalFormat("000.0");
	
	private static final double maxRadarRadius = 30_000.0; // in [m]

	private double speed = 100; // 13 in [m/s] = 46 [km/h] = 25 [kn]

	private List<GeoCoordinate> points = new ArrayList<>();
	private int position = 0;
	private double timeInterval = 5.0; // in [s]
	
	private int radarTrackCounter = 0;
	private String currentRadarTrackId = null;

	public RadarTrack(List<GeoCoordinate> route) {
		init(route);
	}
	
	private void init(List<GeoCoordinate> route) {
		
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
				double newLat = lat1 + factorLat * i * stepLat;
				double newLon = lon1 + factorLon * i * stepLon;
				points.add(new GeoCoordinate(newLat, newLon));
			}
		}
		
		log.info("Created track for GPS with {} trackpoints.", points.size());
	}

	@Override
	public void run() {
		while (!kill) {
			GeoCoordinate current = points.get(position);
			
			position++;
			position = position % points.size();
			
			String msgGpgga = GPSMessages.MSG_GPGGA;
			msgGpgga = msgGpgga.replace("${time}", timeFormatterGps.format(new Date().toInstant()));
			msgGpgga = msgGpgga.replace("${lat}", GeoOps.GeoDecToDegMin(current.getLatitude(), 4, 4));
			if (current.getLatitude() < 0) msgGpgga = msgGpgga.replace("${latNS}", "S");
			else msgGpgga = msgGpgga.replace("${latNS}", "N");
			
			msgGpgga = msgGpgga.replace("${lon}", GeoOps.GeoDecToDegMin(current.getLongitude(), 5, 4));
			if (current.getLongitude() < 0) msgGpgga = msgGpgga.replace("${lonWE}", "W");
			else msgGpgga = msgGpgga.replace("${lonWE}", "E");
			
			String msgGprmc = GPSMessages.MSG_GPRMC;
			msgGprmc = msgGprmc.replace("${time}", timeFormatterGps.format(new Date().toInstant()));
			msgGprmc = msgGprmc.replace("${date}", dateFormatterGps.format(new Date().toInstant()));
			msgGprmc = msgGprmc.replace("${lat}", GeoOps.GeoDecToDegMin(current.getLatitude(), 4, 2) );
			if (current.getLatitude() < 0) msgGprmc = msgGprmc.replace("${latNS}", "S");
			else msgGprmc = msgGprmc.replace("${latNS}", "N");
			
			msgGprmc = msgGprmc.replace("${lon}", GeoOps.GeoDecToDegMin(current.getLongitude(), 5, 2)  );
			if (current.getLongitude() < 0) msgGprmc = msgGprmc.replace("${lonWE}", "W");
			else msgGprmc = msgGprmc.replace("${lonWE}", "E");
			
			msgGpgga = msgGpgga + GeoOps.calcCheckSum(msgGpgga);
			msgGprmc = msgGprmc + GeoOps.calcCheckSum(msgGprmc);
			
			SinkDispatcher.take(Constants.TOKEN_GPS, msgGpgga);
			SinkDispatcher.take(Constants.TOKEN_GPS, msgGprmc);
			
			//Based on GPS position, we check furthermore, if we can find a track plot
			
			GeoCoordinate radarPlot = isInRadiusOfRadarPlot(current.getLatitude(), current.getLongitude());
			
			if (radarPlot != null) {
				if (currentRadarTrackId == null) radarTrackCounter++;
				radarTrackCounter = radarTrackCounter % 100;
				if (radarTrackCounter < 10) currentRadarTrackId = "0" + radarTrackCounter;
				String msgRattm = RadarMessage.MSG_TTM;
				
				double dist = GeoOps.getDistance(current.getLatitude(), current.getLongitude(), radarPlot.getLatitude(), radarPlot.getLongitude());
				double bearing = GeoOps.getBearing(current.getLatitude(), current.getLongitude(), radarPlot.getLatitude(), radarPlot.getLongitude());
				
				dist = dist * Constants.fromMtoNM; // [m] in [NM]
				
				msgRattm = msgRattm.replace("${trackid}", currentRadarTrackId);
				msgRattm = msgRattm.replace("${dist}", df2.format(dist).replace(",", "."));
				msgRattm = msgRattm.replace("${bearing}", df3.format(bearing).replace(",", "."));
				msgRattm = msgRattm.replace("${time}", dateTimeFormatterRadar.format(new Date().toInstant()));
				msgRattm = msgRattm.replace("${name}", "TRK"+currentRadarTrackId);
				
				msgRattm = msgRattm + GeoOps.calcCheckSum(msgRattm);
				
				SinkDispatcher.take(Constants.TOKEN_RADAR, msgRattm);
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
	
	private GeoCoordinate isInRadiusOfRadarPlot(double lat, double lon) {
		for (GeoCoordinate radarPlot : RadarPlots.get()) {
			double dist = GeoOps.getDistance(lat,  lon, radarPlot.getLatitude(),  radarPlot.getLongitude());
			if (dist <= RadarTrack.maxRadarRadius) {
				return radarPlot;
			}
		}
		return null;
	}
}
