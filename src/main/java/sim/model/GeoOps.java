package sim.model;

public class GeoOps {

	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		
		if (lat1 == lat2 && lon1 == lon2) return 0.0;
		
		double R = 6371000.0;
		double phi1 = lat1 * Math.PI/180.0;
		double phi2 = lat2 * Math.PI/180.0;
		double deltaPhi = (lat2-lat1) * Math.PI/180.0;
		double deltaLambda = (lon2-lon1) * Math.PI/180.0;

		double a = Math.sin(deltaPhi/2) * Math.sin(deltaPhi/2) +
		          Math.cos(phi1) * Math.cos(phi2) *
		          Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return (R * c);
	}
	
	public static double getBearing(double lat1, double lon1, double lat2, double lon2) {
		double y = Math.sin(lon2-lon1) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) -
		          Math.sin(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1);
		double angle = Math.atan2(y, x);
		double angleInDeg = (angle*180/Math.PI + 360) % 360;
		
		return ((int)(angleInDeg * 10.0))/10.0;
	}
	
}
