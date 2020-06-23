package sim.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	public static String calcCheckSum(String line) {
		List<Integer> asciivalues = new ArrayList<>();
		for (int i = 0; i < line.length(); i++) {
			char d = line.charAt(i);
			int ascii_value = (int)d;
			asciivalues.add(new Integer(ascii_value));
		}
		
		int result = asciivalues.get(0);
		for (int i = 1; i < asciivalues.size(); i++) {
			result = result^asciivalues.get(i);
		}
		String cs = Integer.toHexString(result).toUpperCase();
		if (cs.length() == 1) cs = "0" +cs;
		return cs;
	}
	
	public static String GeoDecToDegMin(double value, int leadingDigits, int nrDecimals) {
		String decimals = String.join("", Collections.nCopies(nrDecimals, "0"));
		int degree = (int)value;
		double factor = Math.pow(10, nrDecimals);
		
		double rest = (value - (double)degree) * 0.6 * 100.0;
		rest = ((int)(rest * factor)) / factor;
		
		double result = degree * 100.0 + rest;

		String res = "";
		if (leadingDigits == 4) {
			//For latitude
			res = new java.text.DecimalFormat("0000."+decimals).format(result);
		} else {
			//For longitude
			res = new java.text.DecimalFormat("00000."+decimals).format(result);
		}
		res = res.replace(",", ".");
		return res;
	}
	
}
