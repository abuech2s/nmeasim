package sim.data.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Constants;
import sim.model.GeoOps;
import sim.model.tracks.Track;

public class WeatherTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(WeatherTrack.class);

	private double weatherTempStep = 0.1;
	private double windDirStep = 0.1;
	
	public WeatherTrack(Config config) {
		super(config, 1.0, 5.0); //Dummy values
	}

	
	@Override
	public void run() {
		while (!kill) {
				
			String msgWimda = WeatherMessages.MSG_WEATHER;
			
			double barpressure = 1.000;
			msgWimda = msgWimda.replace("${barpressure1}", String.format("%.3f", barpressure).replaceAll(",", "."));
			msgWimda = msgWimda.replace("${barpressure2}", String.format("%.3f", barpressure * Constants.fromMmHgtoBar).replaceAll(",", "."));
			
			msgWimda = msgWimda.replace("${airtemp}", String.format("%.1f", 15.0 + weatherTempStep).replaceAll(",", "."));
			msgWimda = msgWimda.replace("${relhum}", "55.5");
			msgWimda = msgWimda.replace("${abshum}", "60.3");
			msgWimda = msgWimda.replace("${dewpoint}", "23");
			
			msgWimda = msgWimda.replace("${winddir1}", String.format("%.1f", windDirStep).replaceAll(",", "."));
			msgWimda = msgWimda.replace("${winddir2}", String.format("%.1f", windDirStep + 5.0).replaceAll(",", "."));
			
			weatherTempStep += 0.1;
			if (weatherTempStep >= 10.0) weatherTempStep = 0.0;
			
			windDirStep += 0.1;
			if (windDirStep >= 360.0) windDirStep = 0.0;
			
			double winddirInKn = 5.2;
			msgWimda = msgWimda.replace("${windspeed1}", String.format("%.1f", winddirInKn).replaceAll(",", "."));
			msgWimda = msgWimda.replace("${windspeed2}", String.format("%.1f", winddirInKn * Constants.fromKntoMs).replaceAll(",", "."));
			
			msgWimda = "$" + msgWimda + "*" + GeoOps.calcCheckSum(msgWimda);
			
			publish(msgWimda);
			
			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}

		}
	}
}
