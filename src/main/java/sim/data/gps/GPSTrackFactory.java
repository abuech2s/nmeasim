package sim.data.gps;

import java.util.ArrayList;
import java.util.List;

import sim.config.Config;
import sim.model.tracks.ITrack;

public class GPSTrackFactory {

	public static List<ITrack> create(Config config) {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new GPSTrack(config, GPSLines.getRandom());
		threads.add(track);
		return threads;
	}
	
}
