package sim.model;

public class GeoCoordinate {
	
	private double latitude = Double.NaN;
	private double longitude = Double.NaN;
	private String name = "";
	private boolean isAcity = false;
	
	private double factor = 100_000;
	
	public GeoCoordinate(double latitude, double longitude) {
		setCoordinates(latitude, longitude);
	}
	
	public GeoCoordinate(String name, double latitude, double longitude) {
		setCoordinates(latitude, longitude);
		this.name = name;
	}
	
	public GeoCoordinate(String name, double latitude, double longitude, boolean isAcity) {
		setCoordinates(latitude, longitude);
		this.name = name;
		this.isAcity = isAcity;
	}
	
	private void setCoordinates(double latitude, double longitude) {
		this.latitude = ((int)(latitude * factor))/factor;
		this.longitude = ((int)(longitude * factor))/factor;
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
		if (!(o instanceof GeoCoordinate)) return false;
		GeoCoordinate p = (GeoCoordinate)o;
		
		if (p.getLatitude() == this.getLatitude() && p.getLongitude() == this.getLongitude()) return true;
		
		return false;
	}
}
