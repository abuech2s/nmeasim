package sim.data.radar;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Constants;
import sim.data.gps.GPSTrack;
import sim.model.GeoOps;
import sim.model.sinks.ISink;
import sim.model.GeoCoordinate;
import sim.model.tracks.Track;

public class RadarTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(RadarTrack.class);
	
	private DateTimeFormatter dateTimeFormatterRadar = DateTimeFormatter.ofPattern("HHmmss.SS").withZone(ZoneId.systemDefault());
	
	private DecimalFormat df2 = new DecimalFormat("00.0");
	private DecimalFormat df3 = new DecimalFormat("000.0");
	
	private int radarTrackCounter = 0;
	private String currentRadarTrackId = null;
	
	private static GeoCoordinate current = null;
	
	private static ISink sink = null;

	public RadarTrack(Config config) {
		super(config, 100.0, 5.0);
		if (null == sink) sink = getInstance(config);
	}

	@Override
	public void run() {
		while (!kill) {
			
			//Based on GPS position, we check furthermore, if we can find a track plot
			current = GPSTrack.getCurrentPosition();
			
			if (current != null) {
				GeoCoordinate radarPlot = isInRadiusOfRadarPlot(current);
				
				if (radarPlot != null) {
					if (currentRadarTrackId == null) radarTrackCounter++;
					radarTrackCounter = radarTrackCounter % 100;
					
					currentRadarTrackId = String.valueOf(radarTrackCounter);
					if (radarTrackCounter < 10) currentRadarTrackId = "0" + radarTrackCounter;
					String msgRattm = RadarMessage.MSG_TTM;
					
					double distance = GeoOps.getDistance(current.getLatitude(), current.getLongitude(), radarPlot.getLatitude(), radarPlot.getLongitude());
					double bearing = GeoOps.getBearing(current.getLatitude(), current.getLongitude(), radarPlot.getLatitude(), radarPlot.getLongitude());
					
					distance = distance * Constants.fromMtoNM; // [m] in [NM]
					
					msgRattm = msgRattm.replace("${trackid}", currentRadarTrackId);
					msgRattm = msgRattm.replace("${dist}", df2.format(distance).replace(",", "."));
					msgRattm = msgRattm.replace("${bearing}", df3.format(bearing).replace(",", "."));
					msgRattm = msgRattm.replace("${time}", dateTimeFormatterRadar.format(new Date().toInstant()));
					msgRattm = msgRattm.replace("${name}", "TRK"+currentRadarTrackId);
					
					msgRattm = "$" + msgRattm + "*" + GeoOps.calcCheckSum(msgRattm);
					
					sink.take(msgRattm);
				} else {
					currentRadarTrackId = null;
				}
			}

			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}

		}
	}
	
	private GeoCoordinate isInRadiusOfRadarPlot(GeoCoordinate currentPosition) {
		for (GeoCoordinate radarPlot : RadarPlots.get()) {
			double dist = GeoOps.getDistance(currentPosition.getLatitude(), currentPosition.getLongitude(), radarPlot.getLatitude(), radarPlot.getLongitude());
			if (dist <= Constants.MAX_RADAR_RADIUS) {
				return radarPlot;
			}
		}
		return null;
	}
	
	@Override
	protected void killSink() {
		sink.kill();
	}

	@Override
	protected void startSink() {
		sink.start();
	}
}
