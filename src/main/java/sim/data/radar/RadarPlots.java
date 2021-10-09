package sim.data.radar;

import java.util.ArrayList;
import java.util.List;

import sim.model.GeoCoordinate;

public class RadarPlots {

	private static List<RadarPosition> radarPlots = null;
	
	private static void init() {
		radarPlots = new ArrayList<>();
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.68691, 10.27268), "TRK01"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.43219, 10.48448), "TRK02"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.39055, 11.62747), "TRK03"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.61117, 16.67722), "TRK04"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.48617, 14.57153), "TRK05"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.58604, 13.56077), "TRK06"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.20285, 16.13625), "TRK07"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.55006, 10.08963), "TRK08"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.73026, 11.03720), "TRK09"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.15719, 12.99048), "TRK10"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.55740, 15.62488), "TRK11"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.61896, 12.09121), "TRK12"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.69922, 10.78618), "TRK13"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(55.27039, 14.83477), "TRK14"));
		radarPlots.add(new RadarPosition(new GeoCoordinate(54.80201, 13.77155), "TRK15"));
	}
	
	public static List<RadarPosition> get() {
		if (radarPlots == null) init();
		return radarPlots;
	}
	
}
