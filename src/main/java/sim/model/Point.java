package sim.model;

public class Point {
	
	private double latitude = 0.0;
	private double longitude = 0.0;
	private String name = "";
	private boolean isAcity = false;
	
	public Point(double latitude, double longitude) {
		this.latitude = ((int)(latitude * 100000.0))/100000.0;
		this.longitude = ((int)(longitude * 100000.0))/100000.0;
	}
	
	public Point(String name, double latitude, double longitude) {
		this.latitude = ((int)(latitude * 100000.0))/100000.0;
		this.longitude = ((int)(longitude * 100000.0))/100000.0;
		this.name = name;
	}
	
	public Point(String name, double latitude, double longitude, boolean isAcity) {
		this.latitude = ((int)(latitude * 100000.0))/100000.0;
		this.longitude = ((int)(longitude * 100000.0))/100000.0;
		this.name = name;
		this.isAcity = isAcity;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean IsAcity() {
		return isAcity;
	}
	
	@Override
	public String toString() {
		return "(" + name + " : " + latitude + "," + longitude + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point)) return false;
		Point p = (Point)o;
		
		if (p.getLatitude() == this.getLatitude() && p.getLongitude() == this.getLongitude()) return true;
		
		return false;
	}
}
