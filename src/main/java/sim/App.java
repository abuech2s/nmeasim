package sim;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.adsb.ADSBInit;
import sim.ais.data.AISInit;
import sim.gps.GPSInit;
import sim.model.Type;

public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);
	private static int port = 0;
	private static String filename = "";
	private static int nrOfTracks = 10;
	private static Type type = Type.UNKNOWN;
	private static List<Thread> threads = null;

	public static void main(String[] args) {
		
		initParams(args);
		initData();
		
		log.info("Simulator for {} started.", type);
	}
	
	private static void startThreads(List<Thread> threads) {
		for (Thread t : threads) {
			t.setDaemon(false);
			t.start();
			log.info("Start Track : " + t.getName());
		}
	}
	
	private static void startThreads(Thread thread) {
		thread.setDaemon(false);
		thread.start();
		log.info("Start Track : " + thread.getName());
	}
	
	private static void initParams(String[] args) {
		if (args.length < 2) {
			exit();
		}
		
		parseType(args[0]);
		parsePort(args[1]); 
		
		if (type != Type.GPS) {
			if (args.length != 3) {
				exit();
			}
			parseSource(args[2]);
		}
	}
	
	private static void parseType(String typeExp) {
		if (typeExp.equalsIgnoreCase("adsb")) type = Type.ADSB;
		if (typeExp.equalsIgnoreCase("ais")) type = Type.AIS;
		if (typeExp.equalsIgnoreCase("gps")) type = Type.GPS;
		if (type == Type.UNKNOWN) exit("Unknown type: " + typeExp);
	}

	private static void parsePort(String portExp) {
		try {
			port = Integer.parseInt(portExp);
		} catch (Exception e) {
			log.error("Could not parse port {}", e);
			exit();
		}
	}
	
	private static void parseSource(String sourceExp) {
		try {
			nrOfTracks = Integer.parseInt(sourceExp);
		} catch (Exception e) {
			filename = sourceExp;
			exit("Reading track files not yet implemented. Use NrOfTracks.");
		}
	}
	
	private static void initData() {
		
		Thread sinkThread = new Thread(new Sink(port), "Sink");
		startThreads(sinkThread);
		
		switch(type) {
		case ADSB:
			threads = ADSBInit.get(filename, nrOfTracks);
			break;
		case AIS:
			threads = AISInit.get(filename, nrOfTracks);
			break;
		case GPS:
			threads = GPSInit.get(filename);
			break;
		default:
			exit();	
		}
		startThreads(threads);
	}
	
	
	private static void exit(String message) {
		log.error("ERROR: " + message);
		exit();
	}
	
	private static void exit() {
		log.error("");
		log.error("Usage: java -jar *.jar <type> <port> {<NrOfTracks>}");
		log.error("     with type = {adsb, ais, gps}");
		log.error("     with port = <number> - Port");
		log.error("     with NrOfTracks=<number> - Number of generated random tracks. In case of GPS, this param is ignored.");
		//log.error("       OR filename = <Path-To-File> - User defined track file." );
		//log.error("     WARNING: Tracks based on files are not yet implemented.");
		log.error("");
		log.error("Examples:");
		log.error("   java -jar *.jar adsb 6868 50");
		//log.error("   java -jar *.jar adsb 6868 c:\\tmp\\trackfile.txt");
		log.error("   java -jar *.jar ais 6868 50");
		//log.error("   java -jar *.jar ais 6868 c:\\tmp\\trackfile.txt");
		log.error("   java -jar *.jar gps 6868");
		System.exit(-1);
	}

}
