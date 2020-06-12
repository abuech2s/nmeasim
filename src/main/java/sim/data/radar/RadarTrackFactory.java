package sim.data.radar;

import java.util.ArrayList;
import java.util.List;

import sim.data.gps.GPSLines;
import sim.model.tracks.ITrack;

public class RadarTrackFactory {

	public static List<ITrack> create() {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new RadarTrack(GPSLines.get("balticSea"));
		threads.add(track);
		return threads;
	}
}
