package sim.data.radar;

import java.util.ArrayList;
import java.util.List;

import sim.model.GeoCoordinate;

public class RadarPlots {

	private static List<RadarPosition> radarPlots = null;
	
	private static void init() {
		radarPlots = new ArrayList<>();
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.68691, 10.27268), "01"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.43219, 10.48448), "02"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.39055, 11.62747), "03"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.61117, 16.67722), "04"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.48617, 14.57153), "05"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.58604, 13.56077), "06"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.20285, 16.13625), "07"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.55006, 10.08963), "08"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.73026, 11.03720), "09"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.15719, 12.99048), "10"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.55740, 15.62488), "11"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.61896, 12.09121), "12"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.69922, 10.78618), "13"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.27039, 14.83477), "14"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.80201, 13.77155), "15"));
	}
	
	public static List<RadarPosition> get() {
		if (radarPlots == null) init();
		return radarPlots;
	}
	
}