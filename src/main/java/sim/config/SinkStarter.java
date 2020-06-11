package sim.config;

import java.util.ArrayList;
import java.util.List;

import sim.model.sinks.ISink;
import sim.model.sinks.Sink;

public class SinkStarter {

	private static List<ISink> sinks = null;
	
	public static void reInit(Configs configs) {
		if (sinks == null) sinks = new ArrayList<>();
		stopSinkThreads();
		
		for (Config config : configs.getConfigs()) {
			if (config.getActive()) {
				ISink sink = new Sink(config.getType(), config.getPort());
				addSink(sink);
			}
		}
		startSinks();
	}
	
	private static void stopSinkThreads() {
		for (ISink sink : sinks) {
			sink.kill();
		}
		sinks.clear();
	}
	
	private static void addSink(ISink sink) {
		sinks.add(sink);
	}
	
	private static void startSinks() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		for (ISink sink : sinks) {
			sink.start();
		}
	}
	
	public static ISink get(String identifier) {
		for (ISink sink : sinks) {
			if (sink.getIdentifier().equalsIgnoreCase(identifier)) 
				return sink;
		}
		return null;
	}
}
