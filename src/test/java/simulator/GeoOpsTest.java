package simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import sim.util.GeoOps;

public class GeoOpsTest {

	@Test
	public void getDistanceTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		double lat = 1.1234;
		double lon = 2.2468;
		double lat2 = 31.1234;
		double lon2 = 32.2468;

		Method method = GeoOps.class.getDeclaredMethod("getDistance", double.class, double.class, double.class, double.class);
		method.setAccessible(true);
		double returnValue = (double) method.invoke(null, lat, lon, lat2, lon2);
		double result = 4591447.35;
		assertEquals(returnValue, result, 0.2);
	}
	
	@Test
	public void getBearingTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		double lat = 1.1234;
		double lon = 2.2468;
		double lat2 = 31.1234;
		double lon2 = 32.2468;

		Method method = GeoOps.class.getDeclaredMethod("getBearing", double.class, double.class, double.class, double.class);
		method.setAccessible(true);
		double returnValue = (double) method.invoke(null, lat, lon, lat2, lon2);
		double result = 40.4;
		assertEquals(returnValue, result, 0.1);
	}
	
	@Test
	public void getCalcCheckSumTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		//NMEA message without $ and *
		String message = "GPGGA,143903.00,4820.79401,N,01100.57693,E,1,07,1.09,510.7,M,47.8,M,,";

		Method method = GeoOps.class.getDeclaredMethod("calcCheckSum", String.class);
		method.setAccessible(true);
		String returnValue = (String) method.invoke(null, message);
		String result = "5C";
		assertEquals(returnValue, result);
	}
	
	@Test
	public void GeoDecToDegMinTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		double geoDecimalValue = 54.67039;
		int leadingDigits = 4;
		int nrOfDecimals = 4;

		Method method = GeoOps.class.getDeclaredMethod("GeoDecToDegMin", double.class, int.class, int.class);
		method.setAccessible(true);
		String returnValue = (String) method.invoke(null, geoDecimalValue, leadingDigits, nrOfDecimals);
		String result = "5440.2233";
		assertEquals(returnValue, result);
	}

}
