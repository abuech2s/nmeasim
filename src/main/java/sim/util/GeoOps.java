package sim.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GeoOps {

	/**
	 * Calculates the distance between two coordinates in geodecimal format using the Haversine formula
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return distance in [m]
	 */
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		
		if (lat1 == lat2 && lon1 == lon2) return 0.0;
		
		double R = 6_371_000.0; // in [m] 
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
	
	/**
	 * Calculates the bearing between two coordinates using the formula given at
	 * @see <a href="Link">https://www.edwilliams.org/avform.htm#Crs</a>}
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return the bearing in [°]
	 */
	public static double getBearing(double lat1, double lon1, double lat2, double lon2) {
		double deltaL = Math.toRadians(lon2-lon1);
		
		double y = Math.sin(deltaL) * Math.cos(Math.toRadians(lat2));
		
		double x = Math.cos(Math.toRadians(lat1))*Math.sin(Math.toRadians(lat2)) -
		          Math.sin(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.cos(deltaL);
		double angle = Math.atan2(y, x);
		double angleInDeg = (Math.toDegrees(angle) + 360) % 360.0;
		
		return ((int)(angleInDeg * 10.0))/10.0;
	}
	
	/**
	 * Calculates the checksum of a NMEA sentence using XOR operation of each character
	 * 
	 * @param expression The input expression without $ and * signs
	 * @return A 2-digit hexadecimal checksum
	 */
	
	public static String calcCheckSum(String expression) {
		List<Integer> asciivalues = new ArrayList<>();
		for (int i = 0; i < expression.length(); i++) {
			char d = expression.charAt(i);
			int ascii_value = (int)d;
			asciivalues.add(Integer.valueOf(ascii_value));
		}
		
		int result = asciivalues.get(0);
		for (int i = 1; i < asciivalues.size(); i++) {
			result = result^asciivalues.get(i);
		}
		String cs = Integer.toHexString(result).toUpperCase();
		if (cs.length() == 1) cs = "0" + cs;
		return cs;
	}
	
	/**
	 * Calculates a geodecimal coordinate into a specific GPS used DegMinute format.
	 * Latitude value will have four leading numbers.
	 * Longitude value will have five leading numbers.
	 * 
	 * Example: 
	 * Let geoDecValue be 54.67039, leadingDigits be 4 and nrDecimals = 4
	 * then the result would be 5440.2233
	 * 
	 * @param geoDecValue A geo decimal number
	 * @param leadingDigits Number of digits before comma
	 * @param nrDecimals Number of digits after comma
	 * @return Coordinate in GeoMinute format
	 */
	
	public static String GeoDecToDegMin(double geoDecValue, int leadingDigits, int nrDecimals) {
		String decimals = String.join("", Collections.nCopies(nrDecimals, "0"));
		String suffix = String.join("", Collections.nCopies(leadingDigits, "0"));
		int degree = (int)geoDecValue;
		double factor = Math.pow(10, nrDecimals);
		
		double rest = (geoDecValue - (double)degree) * 0.6 * 100.0;
		rest = ((int)(rest * factor)) / factor;
		
		double result = degree * 100.0 + rest;

		return new DecimalFormat(suffix+"."+decimals, new DecimalFormatSymbols(Locale.US)).format(result);
	}
	
}
