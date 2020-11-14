package sim.data.radar;

import java.util.ArrayList;
import java.util.List;

import sim.model.GeoCoordinate;

public class RadarPlots {

	private static List<GeoCoordinate> radarPlots = null;
	
	// This list of radar plots are placed, that a ship can not detect two of them at the same time.
	private static void init() {
		radarPlots = new ArrayList<>();
		radarPlots.add(new GeoCoordinate(54.81037, 10.22113));
		radarPlots.add(new GeoCoordinate(54.43219, 10.48448));
		radarPlots.add(new GeoCoordinate(54.39055, 11.62747));
		radarPlots.add(new GeoCoordinate(54.61117, 16.67722));
		radarPlots.add(new GeoCoordinate(54.48617, 14.57153));
		radarPlots.add(new GeoCoordinate(55.58604, 13.56077));
		radarPlots.add(new GeoCoordinate(55.20285, 16.13625));
	}
	
	public static List<GeoCoordinate> get() {
		if (radarPlots == null) init();
		return radarPlots;
	}
	
}
