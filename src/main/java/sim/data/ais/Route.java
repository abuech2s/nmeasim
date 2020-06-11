package sim.data.ais;

import java.util.List;

import sim.model.Point;

public class Route {

	private List<Point> pathPoints;
	private String startHarbour = "";
	private String endHarbour = "";
	
	public Route(List<Point> points, String startHarbour, String endHarbour) {
		this.startHarbour = startHarbour;
		this.endHarbour = endHarbour;
		this.pathPoints = points;
	}

	public List<Point> getPathPoints() {
		return pathPoints;
	}

	public String getStartHarbour() {
		return startHarbour;
	}

	public String getEndHarbour() {
		return endHarbour;
	}
	
}
