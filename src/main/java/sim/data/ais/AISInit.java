package sim.data.ais;

import java.util.ArrayList;
import java.util.List;

import sim.ais.data.seagraphs.BalticSea;
import sim.model.tracks.ITrack;

public class AISInit {
	public static List<ITrack> get(int nrOfTracks) {
		List<ITrack> threads = new ArrayList<>();
		int mmsi = 100_000_000;
		
		for (int i = 0; i < nrOfTracks; i++) {
			Route route = new BalticSea().getRandomRoute();
			ITrack track = new AISTrack(mmsi+i, route);
			threads.add(track);
		}
		return threads;
	}
}
