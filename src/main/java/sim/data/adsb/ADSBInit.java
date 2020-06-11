package sim.data.adsb;

import java.util.ArrayList;
import java.util.List;

import sim.model.Cities;
import sim.model.tracks.ITrack;

public class ADSBInit {

	public static List<ITrack> get(int nrOfTracks) {
		List<ITrack> threads = new ArrayList<>();
		for (int i = 1; i <= nrOfTracks; i++) {
			//TODO: Random choice for CircularTrack
			ITrack track = new ADSBLinearTrack("HEX"+i, "CS00"+i, Cities.getRandom(), Cities.getRandom());
			threads.add(track);
		}
		return threads;
	}
	
}
