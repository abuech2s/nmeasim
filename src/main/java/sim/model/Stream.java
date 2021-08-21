package sim.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sim.model.sinks.ISink;
import sim.model.tracks.ITrack;

@AllArgsConstructor
@ToString
public class Stream implements IStream {

	@Getter private String streamName;
	@Setter @Getter private List<ITrack> tracks;
	@Setter @Getter private ISink sink;
	
	private static final Logger log = LoggerFactory.getLogger(Stream.class);
	
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
	
	@Override
	public void print() {
		log.info("   {}  ->  Nr of tracks: {} for {}:{}", StringUtils.leftPad(streamName, 8), tracks.size(), sink.getSinkType(), sink.getTarget());
	}
	
	public static IStream getInstance(String streamName, List<ITrack> tracks, ISink sink) {
		for (ITrack track : tracks) {
			track.register(sink);
		}
		return new Stream(streamName.toUpperCase(), tracks, sink);
	}

}
