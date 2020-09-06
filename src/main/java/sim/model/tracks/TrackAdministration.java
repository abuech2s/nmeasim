package sim.model.tracks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Configs;
import sim.config.Constants;
import sim.data.adsb.ADSBTrackFactory;
import sim.data.ais.AISTrackFactory;
import sim.data.gps.GPSTrackFactory;
import sim.data.radar.RadarTrackFactory;
import sim.data.weather.WeatherTrackFactory;

public class TrackAdministration {

	private static final Logger log = LoggerFactory.getLogger(TrackAdministration.class);
	
	private static List<ITrack> tracks = null;
	
	public synchronized static void reInit(Configs configs) {
		if (tracks == null) tracks = new ArrayList<>();
		
		stopTrackThreads();
		
		boolean activateGpsAnyway = false;
		
		for (Config config : configs.getConfigs()) {
			String type = config.getType().toLowerCase().trim();
			switch (type) {
			case Constants.TOKEN_ADSB:
				if (config.isActive()) {
					addTracks(ADSBTrackFactory.create(config.getNroftrack()));
				}
				break;
			case Constants.TOKEN_AIS:
				if (config.isActive()) {
					addTracks(AISTrackFactory.create(config.getNroftrack()));
				}
				break;
			case Constants.TOKEN_GPS:
				if (config.isActive()) {
					activateGpsAnyway = true;
				}
				break;
			case Constants.TOKEN_RADAR:
				if (config.isActive()) {
					addTracks(RadarTrackFactory.create());
					activateGpsAnyway = true;
				}
				break;
			case Constants.TOKEN_WEATHER:
				if (config.isActive()) {
					addTracks(WeatherTrackFactory.create());
					activateGpsAnyway = true;
				}
				break;
			default:
				log.warn("Unknown type: {}. Ignored.", type);
			}
		}
		
		if (activateGpsAnyway) {
			addTracks(GPSTrackFactory.create());
		}
		
		startTrackThreads();
	}
	
	private synchronized static void stopTrackThreads() {
		for (ITrack thread : tracks) {
			try {
				thread.kill();
			} catch (Exception e) {}
		}
		tracks.clear();
	}
	
	private synchronized static void addTracks(List<ITrack> newThreads) {
		tracks.addAll(newThreads);
	}
	
	private synchronized static void startTrackThreads() {
		for (ITrack t : tracks) {
			t.start();
		}
	}
	
}
