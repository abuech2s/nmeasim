package sim.data.radar;

import java.util.ArrayList;
import java.util.List;

import sim.config.Config;
import sim.model.tracks.ITrack;

public class RadarTrackFactory {

	public static List<ITrack> create(Config config) {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new RadarTrack(config);
		threads.add(track);
		return threads;
	}
}
