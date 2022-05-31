package sim.data.adsb;

import java.util.ArrayList;
import java.util.List;

import sim.config.Config;
import sim.model.tracks.ITrack;

public class ADSBTrackFactory {

	public static List<ITrack> create(Config config) {
		List<ITrack> threads = new ArrayList<>();
		for (int i = 1; i <= config.getNroftrack(); i++) {
			ITrack track = new ADSBLinearTrack(config, "HEX"+(i+config.getOffset()), "CS00"+i);
			threads.add(track);
		}
		return threads;
	}
	
}
