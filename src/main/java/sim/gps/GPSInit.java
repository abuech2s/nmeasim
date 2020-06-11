package sim.gps;

import java.util.ArrayList;
import java.util.List;

public class GPSInit {

	public static List<Thread> get(String filename) {
		List<Thread> threads = new ArrayList<>();
		if (filename.isEmpty()) {
			Thread track1 = new Thread(new GPSTrack(GPSLines.getRandom()), "GPS");

			threads.add(track1);
		} else {
			//TODO: Read in track file with defined dummy tracks
		}
		return threads;
	}
	
}
