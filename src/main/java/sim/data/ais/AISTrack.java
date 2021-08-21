package sim.data.ais;

import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.data.ais.data.ship.IShip;
import sim.data.ais.data.ship.ShipFactory;
import sim.config.Config;
import sim.model.GeoCoordinate;
import sim.model.tracks.Track;

public class AISTrack extends Track {

	private static final Logger log = LoggerFactory.getLogger(AISTrack.class);
	
	private Route route = null;

	private int position;
	
	private int mmsi;
	private IShip ship = null;
	
	private int course;
	private int trueHeading;
	private int navStatus;
	private int posFixType;
	
	private long sleeptime;
	
	public AISTrack(Config config, int mmsi, Route route) {
		super(config, 25.0, 5);
		this.mmsi = mmsi;
		this.sleeptime = config.getSleepTime();
		init(route);
	}
	
	private void init(Route route) {
		this.ship = ShipFactory.getRandomShip();
		this.route = route;
		
		Random random = new Random();
		this.timeInterval = random.nextInt(11) + 15; 
		this.speed = (double) (random.nextInt(8) * 5 + 20);
		
		List<GeoCoordinate> routePoints = route.getPathPoints();
		createGeoCoordinates(routePoints);
		
		log.debug("Created track {} for AIS with {} trackpoints.", mmsi, points.size());
	}
	
	@Override
	public void run() {

		while (!kill) {
			GeoCoordinate current = points.get(position);

			position++;
			position = position % points.size();
			
			course++;
			course = course % 360;
			
			trueHeading++;
			trueHeading = trueHeading % 360;
			
			navStatus++;
			navStatus = navStatus % 16;
			
			posFixType++;
			posFixType = posFixType % 16;
			
			String msgType1 = AISEncoder.getBinaryStringMsg1(mmsi, current.getLatitude(), current.getLongitude(), speed, course, trueHeading, navStatus);
			List<String> msgs1 = AISEncoder.getFinalAISMessages(msgType1);
			
			ETA eta = new ETA(position, points.size(), this.timeInterval);
			String binMsg5 = AISEncoder.getBinaryStringMsg5(mmsi, current.getLatitude(), current.getLongitude(), ship, eta.getMonth(), eta.getDay(), eta.getHour(), eta.getMinute(), route.getEndHarbour(), posFixType);
			List<String> msgs5 = AISEncoder.getFinalAISMessages(binMsg5);

			publish(msgs1);
			publish(msgs5);

			try {
				Thread.sleep(timeInterval * 1_000);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
			//If last point of track is received, sleep x minutes and restart it
			if (position == points.size()-1) {
				try {
					log.info("Track for mmsi={} terminated. Restart in {} minutes.", mmsi, sleeptime/60_000);
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					log.warn("Exception at Thread {} ", e);
				}
				log.info("Restart Track for mmsi={}.", mmsi);
				position = 0;
			}
		}
	}

}
