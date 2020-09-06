package sim.data.gps;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Constants;
import sim.model.GeoOps;
import sim.model.GeoCoordinate;
import sim.model.sinks.SinkDispatcher;
import sim.model.tracks.Track;

public class GPSTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(GPSTrack.class);

	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss").withZone(ZoneId.systemDefault());
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyy").withZone(ZoneId.systemDefault());

	private int position = 0;
	
	private static GeoCoordinate currentPosition = null;

	public GPSTrack(List<GeoCoordinate> route) {
		super(100.0, 5.0);
		init(route);
	}
	
	private void init(List<GeoCoordinate> routePoints) {
		createGeoCoordinates(routePoints);
		log.info("Created track for GPS with {} trackpoints.", points.size());
	}
	
	public static GeoCoordinate getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public void run() {
		while (!kill) {
			GeoCoordinate current = points.get(position);
			currentPosition = current;
			
			position++;
			position = position % points.size();
				
			String msgGpgga = GPSMessages.MSG_GPGGA;
			msgGpgga = msgGpgga.replace("${time}", timeFormatter.format(new Date().toInstant()));
			msgGpgga = msgGpgga.replace("${lat}", GeoOps.GeoDecToDegMin(current.getLatitude(), 4, 4) );
			if (current.getLatitude() < 0) msgGpgga = msgGpgga.replace("${latNS}", "S");
			else msgGpgga = msgGpgga.replace("${latNS}", "N");
							
			msgGpgga = msgGpgga.replace("${lon}", GeoOps.GeoDecToDegMin(current.getLongitude(), 5, 4));
			if (current.getLongitude() < 0) msgGpgga = msgGpgga.replace("${lonWE}", "W");
			else msgGpgga = msgGpgga.replace("${lonWE}", "E");
			
			String msgGprmc = GPSMessages.MSG_GPRMC;
			msgGprmc = msgGprmc.replace("${lat}", GeoOps.GeoDecToDegMin(current.getLatitude(), 4, 2));
			if (current.getLatitude() < 0) msgGprmc = msgGprmc.replace("${latNS}", "S");
			else msgGprmc = msgGprmc.replace("${latNS}", "N");
			msgGprmc = msgGprmc.replace("${lon}", GeoOps.GeoDecToDegMin(current.getLongitude(), 5, 2));
			if (current.getLongitude() < 0) msgGprmc = msgGprmc.replace("${lonWE}", "W");
			else msgGprmc = msgGprmc.replace("${lonWE}", "E");
			msgGprmc = msgGprmc.replace("${time}", timeFormatter.format(new Date().toInstant()));
			msgGprmc = msgGprmc.replace("${date}", dateFormatter.format(new Date().toInstant()));
			
			msgGpgga = "$" + msgGpgga + "*" + GeoOps.calcCheckSum(msgGpgga);
			msgGprmc = "$" + msgGprmc + "*" + GeoOps.calcCheckSum(msgGprmc);
			
			SinkDispatcher.take(Constants.TOKEN_GPS, msgGpgga);
			SinkDispatcher.take(Constants.TOKEN_GPS, msgGprmc);
			
			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}
		}
	}
}
