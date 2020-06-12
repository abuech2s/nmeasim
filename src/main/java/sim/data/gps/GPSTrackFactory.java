package sim.data.gps;

import java.util.ArrayList;
import java.util.List;

import sim.model.tracks.ITrack;

public class GPSTrackFactory {

	public static List<ITrack> create() {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new GPSTrack(GPSLines.getRandom());
		threads.add(track);
		return threads;
	}
	
}
