package sim.model.tracks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.model.GeoCoordinate;
import sim.model.GeoOps;
import sim.model.sinks.ISink;
import sim.model.sinks.TCPSink;
import sim.model.sinks.UDPSink;

public abstract class Track implements ITrack {
	
	private static final Logger log = LoggerFactory.getLogger(Track.class);

	protected boolean kill = false;
	protected Thread trackThread;
	
	protected double speed = Double.NaN;
	protected double timeInterval = Double.NaN;
	protected List<GeoCoordinate> points = new ArrayList<>();
	protected int position = 0;
	
	protected abstract void killSink();
	protected abstract void startSink();
	
	public Track(Config config, double speed, double timeInterval) {
		this.speed = speed;
		this.timeInterval = timeInterval;
	}
	
	public void kill() {
		killSink();
		this.kill = true;
	}
	
	@Override
	public void start() {
		startSink();
		trackThread = new Thread(this);
		trackThread.setDaemon(false);
		trackThread.start();
	}
	
	protected void createGeoCoordinates(List<GeoCoordinate> routePoints) {
		for (int p = 0; p < routePoints.size()-1; p++) {
			double lat1 = routePoints.get(p).getLatitude();
			double lon1 = routePoints.get(p).getLongitude();
			double lat2 = routePoints.get(p+1).getLatitude();
			double lon2 = routePoints.get(p+1).getLongitude();

			double distance = Math.abs(GeoOps.getDistance(routePoints.get(p).getLatitude(), routePoints.get(p).getLongitude(),
					routePoints.get(p+1).getLatitude(), routePoints.get(p+1).getLongitude()));
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
	}
	
	protected ISink getInstance(Config config) {
		switch (config.getSink().toLowerCase()) {
		case "tcp":
			return new TCPSink(config.getType(), config.getPort());
		case "udp":
			return new UDPSink(config.getType(), config.getIp(), config.getPort());
		default:
			log.warn("Unknown sink type: {}", config);
		}

		return null;
	}
}
