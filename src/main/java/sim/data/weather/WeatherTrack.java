package sim.data.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.config.Constants;
import sim.model.GeoOps;
import sim.model.sinks.ISink;
import sim.model.tracks.Track;

public class WeatherTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(WeatherTrack.class);
	
	private static ISink sink = null;

	public WeatherTrack(Config config) {
		super(config, 1.0, 5.0); //Dummy values
		if (null == sink) sink = getInstance(config);
	}

	@Override
	public void run() {
		while (!kill) {
				
			String msgWimda = WeatherMessages.MSG_WEATHER;
			
			double barpressure = 1.000;
			msgWimda = msgWimda.replace("${barpressure1}", String.format("%.3f", barpressure));
			msgWimda = msgWimda.replace("${barpressure2}", String.format("%.3f", barpressure * Constants.fromMmHgtoBar));
			
			msgWimda = msgWimda.replace("${airtemp}", "25.3");
			msgWimda = msgWimda.replace("${relhum}", "55.5");
			msgWimda = msgWimda.replace("${abshum}", "60.3");
			msgWimda = msgWimda.replace("${dewpoint}", "23");
			
			msgWimda = msgWimda.replace("${winddir1}", "273.5");
			msgWimda = msgWimda.replace("${winddir2}", "279.1");
			
			double winddirInKn = 5.2;
			msgWimda = msgWimda.replace("${windspeed1}", String.format("%.1f", winddirInKn));
			msgWimda = msgWimda.replace("${windspeed2}", String.format("%.1f", winddirInKn * Constants.fromKntoMs));
			
			msgWimda = "$" + msgWimda + "*" + GeoOps.calcCheckSum(msgWimda);
			
			sink.take(msgWimda);
			
			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}

		}
	}
	
	@Override
	protected void killSink() {
		sink.kill();
	}

	@Override
	protected void startSink() {
		sink.start();
	}

}
