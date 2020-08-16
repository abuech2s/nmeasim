package sim.model;

import lombok.Getter;
import lombok.ToString;

@ToString
public class GeoCoordinate {
	
	@Getter private double latitude = Double.NaN;
	@Getter private double longitude = Double.NaN;
	@Getter private String name = "";
	@Getter private boolean isAcity = false;
	
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
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GeoCoordinate)) return false;
		GeoCoordinate p = (GeoCoordinate)o;
		
		if (p.getLatitude() == this.getLatitude() && p.getLongitude() == this.getLongitude()) return true;
		
		return false;
	}
}
