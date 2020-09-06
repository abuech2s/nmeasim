package sim.data.radar;

import java.util.ArrayList;
import java.util.List;

import sim.model.tracks.ITrack;

public class RadarTrackFactory {

	public static List<ITrack> create() {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new RadarTrack();
		threads.add(track);
		return threads;
	}
}
