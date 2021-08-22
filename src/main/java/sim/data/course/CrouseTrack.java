package sim.data.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Config;
import sim.model.tracks.Track;
import sim.util.GeoOps;

public class CrouseTrack extends Track {
	
	private static final Logger log = LoggerFactory.getLogger(CrouseTrack.class);

	private double courseTempStep = 0.1;
	
	public CrouseTrack(Config config) {
		super(config, 1.0, 5); //Dummy values
	}

	@Override
	public void run() {
		while (!kill) {
				
			String msgGphdt = CourseMessages.MSG_GPHDT;
			msgGphdt = msgGphdt.replace("${course}", String.format("%.3f", courseTempStep).replaceAll(",", "."));
			
			String msgHehdt = CourseMessages.MSG_HEHDT;
			msgHehdt = msgHehdt.replace("${course}", String.format("%.3f", courseTempStep).replaceAll(",", "."));
			
			courseTempStep += 0.1;
			if (courseTempStep >= 360.0) courseTempStep = 0.0;
			
			msgGphdt = "$" + msgGphdt + "*" + GeoOps.calcCheckSum(msgGphdt);
			msgHehdt = "$" + msgHehdt + "*" + GeoOps.calcCheckSum(msgHehdt);
			
			publish(msgGphdt);
			publish(msgHehdt);
			
			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}

		}
	}
}
