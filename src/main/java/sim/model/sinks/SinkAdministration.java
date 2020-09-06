package sim.model.sinks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Configs;

public class SinkAdministration {

	private static List<ISink> sinks = null;
	private static final Logger log = LoggerFactory.getLogger(SinkAdministration.class);
	
	public synchronized static void reInit(Configs configs) {
		if (sinks == null) sinks = new ArrayList<>();
		stopSinkThreads();
		
		for (Config config : configs.getConfigs()) {
			if (config.isActive()) {
				ISink sink = getInstance(config);
				if (sink != null) addSink(sink);
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
	
	private static ISink getInstance(Config config) {
		switch (config.getSink().toLowerCase()) {
		case "tcp":
			return new TCPSink(config.getType(), config.getPort());
		case "udp":
			return new UDPSink(config.getType(), config.getIp(), config.getPort());
		default:
			log.warn("Unknown sink type: {}", config);
		}

		return null;
	}
	
	private static void addSink(ISink sink) {
		sinks.add(sink);
	}
	
	private synchronized static void startSinks() {
		try {
			// We wait a moment, to be sure that all legacy ports are closed
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
