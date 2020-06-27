package sim.data.ais;

import java.util.List;

import sim.model.GeoCoordinate;

public class Route {

	private List<GeoCoordinate> pathPoints;
	private String startHarbour = "";
	private String endHarbour = "";
	
	public Route(List<GeoCoordinate> points, String startHarbour, String endHarbour) {
		this.startHarbour = startHarbour;
		this.endHarbour = endHarbour;
		this.pathPoints = points;
	}

	public List<GeoCoordinate> getPathPoints() {
		return pathPoints;
	}

	public String getStartHarbour() {
		return startHarbour;
	}

	public String getEndHarbour() {
		return endHarbour;
	}
	
}
