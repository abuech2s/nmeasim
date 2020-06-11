package sim;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.SinkStarter;
import sim.model.sinks.ISink;

public class SinkDispatcher {
	
	private static final Logger log = LoggerFactory.getLogger(SinkDispatcher.class);

	public static void take(String sinkIdentifier, String item) {
		ISink sink = SinkStarter.get(sinkIdentifier);
		if (sink == null) {
			log.info("Sink with identifier {} does not exist.", sinkIdentifier);
		} else {
			sink.take(item);
		}
	}
	
	public static void take(String sinkIdentifier, List<String> items) {
		ISink sink = SinkStarter.get(sinkIdentifier);
		if (sink == null) {
			log.info("Sink with identifier {} does not exist.", sinkIdentifier);
		} else {
			sink.take(items);
		}
	}

}
