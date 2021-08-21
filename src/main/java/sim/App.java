package sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Configuration;
import sim.util.Version;

public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {	
		if (args.length > 0 && args[0].endsWith("-help")) help();
		
		initConfiguration();
		log.info("Version: {} (Build: {})", Version.get("Version"), Version.get("Date"));
		log.info("Simulator started.\r\n");
	}
	
	private static void initConfiguration() {
		Thread configurationThread = new Thread(new Configuration(), "Configuration");
		configurationThread.setDaemon(false);
		configurationThread.start();
	}
	
	public static void exit(String message) {
		log.error("ERROR: {}", message);
		System.exit(-1);
	}
	
	public static void help() {
		log.error("");
		log.error("Usage: java -jar *.jar");
		log.error("");
		log.error("config.xml must exist in the same directory.");
		System.exit(-1);
	}

}
