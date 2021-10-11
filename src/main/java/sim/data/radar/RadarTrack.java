package sim.data.radar;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Constants;
import sim.data.gps.GPSTrack;
import sim.model.GeoOps;
import sim.model.GeoCoordinate;
import sim.model.tracks.Track;

public class RadarTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(RadarTrack.class);
	
	private DateTimeFormatter dateTimeFormatterRadar = DateTimeFormatter.ofPattern("HHmmss.SS").withZone(ZoneId.of("UTC"));
	
	private DecimalFormat df2 = new DecimalFormat("00.0");
	private DecimalFormat df3 = new DecimalFormat("000.0");
	
	private String currentRadarTrackId = null;
	
	private static GeoCoordinate current = null;
	private double course = 0.0;

	public RadarTrack(Config config) {
		super(config, 100.0, 5);
	}

	@Override
	public void run() {
		while (!kill) {
			
			//Based on GPS position, we check furthermore, if we can find a track plot
			current = GPSTrack.getCurrentPosition();
			
			if (current != null) {
				List<RadarPosition> radarPositions = isInRadiusOfRadarPosition(current);
				List<String> messages = new ArrayList<>();
				
				
				if (!radarPositions.isEmpty()) {
					for (RadarPosition rp : radarPositions) {
						
						course += 0.1;
						if (course >= 360.0) course = course - 360.0;
						currentRadarTrackId = rp.getTrackName();
						
						String msgRattm = RadarMessage.MSG_TTM;
						
						double distance = GeoOps.getDistance(current.getLatitude(), current.getLongitude(), rp.getGeoCoordinate().getLatitude(), rp.getGeoCoordinate().getLongitude());
						double bearing = GeoOps.getBearing(current.getLatitude(), current.getLongitude(), rp.getGeoCoordinate().getLatitude(), rp.getGeoCoordinate().getLongitude());
						
						distance = distance * Constants.fromMtoNM; // [m] in [NM]
						
						msgRattm = msgRattm.replace("${trackid}", currentRadarTrackId);
						msgRattm = msgRattm.replace("${dist}", df2.format(distance).replace(",", "."));
						msgRattm = msgRattm.replace("${bearing}", df3.format(bearing).replace(",", "."));
						msgRattm = msgRattm.replace("${time}", dateTimeFormatterRadar.format(new Date().toInstant()));
						msgRattm = msgRattm.replace("${name}", "TRK"+currentRadarTrackId);
						msgRattm = msgRattm.replace("${course}", df3.format(course).replace(",", "."));
						msgRattm = msgRattm.replace("${speed}", String.format("%.3f", speed * Constants.fromMstoKn).replaceAll(",", "."));
						
						msgRattm = "$" + msgRattm + "*" + GeoOps.calcCheckSum(msgRattm);
						
						messages.add(msgRattm);		
					}
					publish(messages);
					
				}
			}

			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.debug("Exception: ", e);
			}

		}
	}
	
	private List<RadarPosition> isInRadiusOfRadarPosition(GeoCoordinate currentPosition) {
		List<RadarPosition> hits = new ArrayList<>();
		for (RadarPosition radarPosition : RadarPlots.get()) {
			double dist = GeoOps.getDistance(currentPosition.getLatitude(), currentPosition.getLongitude(), radarPosition.getGeoCoordinate().getLatitude(), radarPosition.getGeoCoordinate().getLongitude());
			if (dist <= Constants.MAX_RADAR_RADIUS) {
				hits.add(radarPosition);
			}
		}
		return hits;
	}
}
