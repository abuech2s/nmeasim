package sim.ais.data;

import java.util.ArrayList;
import java.util.List;

import sim.ais.data.seagraphs.BalticSea;

public class AISInit {
	public static List<Thread> get(String filename, int nrOfTracks) {
		List<Thread> threads = new ArrayList<>();
		if (filename.isEmpty()) {
			
			int mmsi = 100_000_000;
			
			for (int i = 0; i < nrOfTracks; i++) {
				Route route = new BalticSea().getRandomRoute();
				Thread track = new Thread(new AISTrack(mmsi+i, route), "AIS_"+(mmsi+i));
				threads.add(track);
			}
			
		} else {
			//TODO: Read in track file with defined dummy tracks
		}
		return threads;
	}
}
