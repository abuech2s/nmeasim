package sim.model.tracks;

import java.util.ArrayList;
import java.util.List;

import sim.config.Config;
import sim.config.Configs;
import sim.data.adsb.ADSBTrackFactory;
import sim.data.ais.AISTrackFactory;
import sim.data.gps.GPSTrackFactory;

public class TrackAdministration {

	private static List<ITrack> tracks = null;
	
	public static void reInit(Configs configs) {
		if (tracks == null) tracks = new ArrayList<>();
		
		stopTrackThreads();
		
		for (Config config : configs.getConfigs()) {
			switch (config.getType().toLowerCase().trim()) {
			case "adsb":
				if (config.getActive()) {
					addTracks(ADSBTrackFactory.create(config.getNroftrack()));
				}
				break;
			case "ais":
				if (config.getActive()) {
					addTracks(AISTrackFactory.create(config.getNroftrack()));
				}
				break;
			case "gps":
				if (config.getActive()) {
					addTracks(GPSTrackFactory.create());
				}
				break;
			}
		}
		
		startTrackThreads();
	}
	
	private static void stopTrackThreads() {
		for (ITrack thread : tracks) {
			try {
				thread.kill();
			} catch (Exception e) {}
		}
		tracks.clear();
	}
	
	private static void addTracks(List<ITrack> newThreads) {
		tracks.addAll(newThreads);
	}
	
	private static void startTrackThreads() {
		for (ITrack t : tracks) {
			t.start();
		}
	}
	
}
