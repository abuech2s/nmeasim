package sim.data.ais;

import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.data.ais.data.ship.IShip;
import sim.data.ais.data.ship.ShipFactory;
import sim.config.Constants;
import sim.model.GeoCoordinate;
import sim.model.sinks.SinkDispatcher;
import sim.model.tracks.Track;

public class AISTrack extends Track {

	private static final Logger log = LoggerFactory.getLogger(AISTrack.class);
	
	private Route route = null;

	private int position = 0;
	private double totalTime = 0.0;
	
	private int mmsi = 0;
	private IShip ship = null;
	
	public AISTrack(int mmsi, Route route) {
		super(25.0, 5.0);
		this.mmsi = mmsi;
		init(route);
	}
	
	private void init(Route route) {
		this.ship = ShipFactory.getRandomShip();
		this.route = route;
		this.totalTime = 0.0;
		
		Random random = new Random();
		this.timeInterval = (double) (random.nextInt(11) + 15); 
		this.speed = (double) (random.nextInt(8) * 5 + 20);
		
		List<GeoCoordinate> routePoints = route.getPathPoints();
		createGeoCoordinates(routePoints);
		
		log.info("Created track {} for AIS with {} trackpoints, duration {} s.", mmsi, points.size(), (int)this.totalTime);
	}
	
	@Override
	public void run() {

		while (!kill) {
			GeoCoordinate current = points.get(position);

			position++;
			position = position % points.size();

			String msgType1 = AISEncoder.getBinaryStringMsg1(mmsi, current.getLatitude(), current.getLongitude(), speed);
			List<String> msgs1 = AISEncoder.getFinalAISMessages(msgType1);
			
			//TODO: ETA
			String binMsg5 = AISEncoder.getBinaryStringMsg5(mmsi, current.getLatitude(), current.getLongitude(), ship, 0, 0, 0, 0, route.getEndHarbour());
			List<String> msgs5 = AISEncoder.getFinalAISMessages(binMsg5);

			SinkDispatcher.take(Constants.TOKEN_AIS, msgs1);
			SinkDispatcher.take(Constants.TOKEN_AIS, msgs5);

			try {
				Thread.sleep((long)(timeInterval * 1000L));
			} catch (InterruptedException e) {
				log.warn("Exception at Thread {} ", e);
			}
			
			//If last point of track is received, sleep x minutes and restart it
			if (position == points.size()-1) {
				try {
					log.info("Track for mmsi={} terminated. Restart in {} minutes.", mmsi, Constants.TRACK_SLEEP_TIME/60000);
					Thread.sleep(Constants.TRACK_SLEEP_TIME);
				} catch (InterruptedException e) {
					log.warn("Exception at Thread {} ", e);
				}
				log.info("Restart Track for mmsi={}.", mmsi);
				position = 0;
			}
		}
	}

}
