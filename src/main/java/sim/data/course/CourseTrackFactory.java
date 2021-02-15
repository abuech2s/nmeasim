package sim.data.course;

import java.util.ArrayList;
import java.util.List;

import sim.config.Config;
import sim.model.tracks.ITrack;

public class CourseTrackFactory {

	public static List<ITrack> create(Config config) {
		List<ITrack> threads = new ArrayList<>();
		ITrack track = new CrouseTrack(config);
		threads.add(track);
		return threads;
	}
	
}
