package sim.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sim.model.sinks.ISink;
import sim.model.tracks.ITrack;

@AllArgsConstructor
@ToString
public class Stream implements IStream {

	@Setter @Getter List<ITrack> tracks;
	@Setter @Getter ISink sink;
	
	@Override
	public void start() {
		if (null != sink) sink.start();
		for (ITrack t : tracks) {
			t.start();
		}
	}
	
	@Override
	public void kill() {
		for (ITrack t : tracks) {
			t.kill();
		}
		if (null != sink) sink.kill();
	}
	
	public static IStream getInstance(List<ITrack> tracks, ISink sink) {
		for (ITrack track : tracks) {
			track.register(sink);
		}
		return new Stream(tracks, sink);
	}

}
