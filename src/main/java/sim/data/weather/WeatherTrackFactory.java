package sim.data.weather;

import java.util.ArrayList;
import java.util.List;

import sim.config.Config;
import sim.model.tracks.ITrack;

public class WeatherTrackFactory {

	public static List<ITrack> create(Config config) {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new WeatherTrack(config);
		threads.add(track);
		return threads;
	}
	
}
