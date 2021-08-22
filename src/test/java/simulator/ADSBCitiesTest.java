package simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import sim.data.adsb.data.Cities;
import sim.model.GeoCoordinate;

public class ADSBCitiesTest {

	@Test
	public void getCitiesTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		String city = "berlin";

		Method method = Cities.class.getDeclaredMethod("get", String.class);
		method.setAccessible(true);
		GeoCoordinate returnValue = (GeoCoordinate) method.invoke(null, city);
		GeoCoordinate result = new GeoCoordinate(52.5218, 13.4015);
		assertEquals(returnValue.getLatitude(), result.getLatitude());
		assertEquals(returnValue.getLongitude(), result.getLongitude());
	}
	
}
