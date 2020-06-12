package sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Configuration;

public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {	
		if (args.length > 0 && args[0].endsWith("-help")) exit();
		initConfiguration();
		log.info("Simulator started.");
	}
	
	private static void initConfiguration() {
		Thread configurationThread = new Thread(new Configuration(), "Configuration");
		configurationThread.setDaemon(false);
		configurationThread.start();
	}
	
	private static void exit() {
		log.error("");
		log.error("Usage: java -jar *.jar");
		log.error("");
		log.error("config.xml must exist in the same directory.");
		System.exit(-1);
	}

}
