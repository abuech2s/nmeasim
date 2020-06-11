package sim.data.gps;

import java.util.ArrayList;
import java.util.List;

import sim.model.tracks.ITrack;

public class GPSInit {

	public static List<ITrack> get() {
		List<ITrack> threads = new ArrayList<>();
		ITrack track1 = new GPSTrack(GPSLines.getRandom());
		threads.add(track1);
		return threads;
	}
	
}
