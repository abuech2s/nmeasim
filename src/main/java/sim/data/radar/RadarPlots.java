package sim.data.radar;

import java.util.ArrayList;
import java.util.List;

import sim.model.GeoCoordinate;

public class RadarPlots {

	private static List<GeoCoordinate> radarPlots = null;
	
	// This list of radar plots are placed, that a ship can not detect two of them at the same time.
	private static void init() {
		radarPlots = new ArrayList<>();
		radarPlots.add(new GeoCoordinate(54.6246, 10.45597));
		radarPlots.add(new GeoCoordinate(54.35936, 11.79639));
		radarPlots.add(new GeoCoordinate(54.98357, 13.25297));
		radarPlots.add(new GeoCoordinate(54.81272, 15.87781));
	}
	
	public static List<GeoCoordinate> get() {
		if (radarPlots == null) init();
		return radarPlots;
	}
	
}
