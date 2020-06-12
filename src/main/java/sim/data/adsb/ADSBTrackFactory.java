package sim.data.adsb;

import java.util.ArrayList;
import java.util.List;

import sim.model.tracks.ITrack;

public class ADSBTrackFactory {

	public static List<ITrack> create(int nrOfTracks) {
		List<ITrack> threads = new ArrayList<>();
		for (int i = 1; i <= nrOfTracks; i++) {
			//TODO: Integrate CircularTrack
			ITrack track = new ADSBLinearTrack("HEX"+i, "CS00"+i);
			threads.add(track);
		}
		return threads;
	}
	
}
