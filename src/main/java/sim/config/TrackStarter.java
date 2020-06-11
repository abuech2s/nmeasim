package sim.config;

import java.util.ArrayList;
import java.util.List;

import sim.data.adsb.ADSBInit;
import sim.data.ais.AISInit;
import sim.data.gps.GPSInit;
import sim.model.tracks.ITrack;

public class TrackStarter {

	private static List<ITrack> tracks = null;
	
	public static void reInit(Configs configs) {
		if (tracks == null) tracks = new ArrayList<>();
		stopTrackThreads();
		
		for (Config config : configs.getConfigs()) {
			switch (config.getType().toLowerCase().trim()) {
			case "adsb":
				if (config.getActive()) {
					addTracks(ADSBInit.get(config.getNroftrack()));
				}
				break;
			case "ais":
				if (config.getActive()) {
					addTracks(AISInit.get(config.getNroftrack()));
				}
				break;
			case "gps":
				if (config.getActive()) {
					addTracks(GPSInit.get());
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
