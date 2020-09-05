package sim.data.weather;

import java.util.ArrayList;
import java.util.List;

import sim.model.tracks.ITrack;

public class WeatherTrackFactory {

	public static List<ITrack> create() {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new WeatherTrack();
		threads.add(track);
		return threads;
	}
	
}
