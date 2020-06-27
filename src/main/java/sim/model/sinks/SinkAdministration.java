package sim.model.sinks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Configs;
import sim.config.Constants;

public class SinkAdministration {

	private static List<ISink> sinks = null;
	private static final Logger log = LoggerFactory.getLogger(SinkAdministration.class);
	
	public synchronized static void reInit(Configs configs) {
		if (sinks == null) sinks = new ArrayList<>();
		stopSinkThreads();
		
		for (Config config : configs.getConfigs()) {
			if (config.getActive()) {
				ISink sink = new Sink(config.getType(), config.getPort());
				addSink(sink);
			}
			if (config.getType().equalsIgnoreCase(Constants.TOKEN_RADAR) && config.getActive()) {
				Config gpsConfig = configs.getConfig(Constants.TOKEN_GPS);
				ISink sink = new Sink(gpsConfig.getType(), gpsConfig.getPort());
				addSink(sink);
			}
		}
		startSinks();
	}
	
	private synchronized static void stopSinkThreads() {
		for (ISink sink : sinks) {
			sink.kill();
		}
		sinks.clear();
	}
	
	private static void addSink(ISink sink) {
		sinks.add(sink);
	}
	
	private synchronized static void startSinks() {
		try {
			//We wait a moment, to be sure that all legacy ports are closed
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			log.warn("Exception: {}", e1);
		}
		
		for (ISink sink : sinks) {
			sink.start();
		}
	}
	
	public synchronized static ISink get(String identifier) {
		for (ISink sink : sinks) {
			if (sink.getIdentifier().equalsIgnoreCase(identifier)) 
				return sink;
		}
		return null;
	}
}
