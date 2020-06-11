package sim.adsb;

import java.util.ArrayList;
import java.util.List;

import sim.model.Cities;

public class ADSBInit {

	public static List<Thread> get(String filename, int nrOfTracks) {
		List<Thread> threads = new ArrayList<>();
		if (filename.isEmpty()) {

			for (int i = 0; i < nrOfTracks; i++) {
				//TODO: Random choice for CircularTrack
				Thread track = new Thread(new ADSBLinearTrack("HEX"+i, "CS00"+i, Cities.getRandom(), Cities.getRandom()), "Track"+i);
				threads.add(track);
			}

		} else {
			//TODO: Read in track file with defined dummy tracks
		}
		return threads;
	}
	
}
