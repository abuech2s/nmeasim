package sim.model.tracks;

import java.util.ArrayList;
import java.util.List;

import sim.config.Config;
import sim.config.Configs;
import sim.config.Constants;
import sim.data.adsb.ADSBTrackFactory;
import sim.data.ais.AISTrackFactory;
import sim.data.gps.GPSTrackFactory;
import sim.data.radar.RadarTrackFactory;

public class TrackAdministration {

	private static List<ITrack> tracks = null;
	
	public synchronized static void reInit(Configs configs) {
		if (tracks == null) tracks = new ArrayList<>();
		
		stopTrackThreads();
		
		for (Config config : configs.getConfigs()) {
			switch (config.getType().toLowerCase().trim()) {
			case Constants.TOKEN_ADSB:
				if (config.getActive()) {
					addTracks(ADSBTrackFactory.create(config.getNroftrack()));
				}
				break;
			case Constants.TOKEN_AIS:
				if (config.getActive()) {
					addTracks(AISTrackFactory.create(config.getNroftrack()));
				}
				break;
			case Constants.TOKEN_GPS:
				//GPS will be automatically activated in case of Radar
				if (config.getActive() && !configs.getConfig(Constants.TOKEN_RADAR).getActive()) {
					addTracks(GPSTrackFactory.create());
				}
				break;
			case Constants.TOKEN_RADAR:
				if (config.getActive()) {
					addTracks(RadarTrackFactory.create());
				}
				break;
			}
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
