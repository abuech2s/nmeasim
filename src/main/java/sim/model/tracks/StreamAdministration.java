package sim.model.tracks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Configs;
import sim.config.Constants;
import sim.data.adsb.ADSBTrackFactory;
import sim.data.ais.AISTrackFactory;
import sim.data.course.CourseTrackFactory;
import sim.data.gps.GPSTrackFactory;
import sim.data.radar.RadarTrackFactory;
import sim.data.weather.WeatherTrackFactory;
import sim.model.IStream;
import sim.model.Stream;
import sim.model.sinks.ISink;
import sim.model.sinks.TCPSink;
import sim.model.sinks.UDPSink;

public class StreamAdministration {

	private static final Logger log = LoggerFactory.getLogger(StreamAdministration.class);
	
	private static List<IStream> streams = null;
	
	public synchronized static void reInit(Configs configs) {
		if (streams == null) streams = new ArrayList<>();
		
		stopStreams();
		
		for (Config config : configs.getConfigs()) {
			String type = config.getType().toLowerCase().strip();
			switch (type) {
			case Constants.TOKEN_ADSB:
				if (config.isActive()) {
					streams.add(Stream.getInstance(Constants.TOKEN_ADSB, ADSBTrackFactory.create(config), createSink(config)));
				}
				break;
			case Constants.TOKEN_AIS:
				if (config.isActive()) {
					streams.add(Stream.getInstance(Constants.TOKEN_AIS, AISTrackFactory.create(config), createSink(config)));
				}
				break;
			case Constants.TOKEN_GPS:
				if (config.isActive()) {
					streams.add(Stream.getInstance(Constants.TOKEN_GPS, GPSTrackFactory.create(config), createSink(config)));
				}
				break;
			case Constants.TOKEN_RADAR:
				if (config.isActive()) {
					streams.add(Stream.getInstance(Constants.TOKEN_RADAR, RadarTrackFactory.create(config), createSink(config)));
				}
				break;
			case Constants.TOKEN_WEATHER:
				if (config.isActive()) {
					streams.add(Stream.getInstance(Constants.TOKEN_WEATHER, WeatherTrackFactory.create(config), createSink(config)));
				}
				break;	
			case Constants.TOKEN_COURSE:
				if (config.isActive()) {
					streams.add(Stream.getInstance(Constants.TOKEN_COURSE, CourseTrackFactory.create(config), createSink(config)));
				}
				break;
			default:
				log.warn("Unknown type: {}. Ignored.", type);
			}
		}
		
		printStreams();
		startStreams();
	}
	
	private static ISink createSink(Config config) {
		switch (config.getSink().toLowerCase().strip()) {
		case Constants.TOKEN_TCP:
			return new TCPSink(config.getType(), config.getPort());
		case Constants.TOKEN_UDP:
			return new UDPSink(config.getType(), config.getIp(), config.getPort());
		default:
			log.warn("Unknown sink type: {}", config);
		}

		return null;
	}
	
	private static void stopStreams() {
		for (IStream stream: streams) {
			stream.kill();
		}
		streams.clear();
	}
	
	private static void startStreams() {
		for (IStream stream: streams) {
			stream.start();
		}
	}
	
	private static void printStreams() {
		log.info("Created Streams:");
		for (IStream stream : streams) {
			log.info("   {}", stream.print());
		}
	}
	
}
