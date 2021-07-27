package simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import sim.data.ais.AISEncoder;
import sim.data.ais.data.ship.IShip;
import sim.data.ais.data.ship.ShipFactory;

public class AisMessagesTest {

	@Test
	public void AisShipGenerationTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	
		int mmsi = 100123000;
		double lat = 1.1234;
		double lon = 2.2468;
		double speed = 7.654;
		int course = 0;
		int trueHeading = 0;
		int navStatus = 0;

		Method method = AISEncoder.class.getDeclaredMethod("getBinaryStringMsg1", int.class, double.class, double.class, double.class, int.class, int.class, int.class);
		method.setAccessible(true);
		String returnValue = (String) method.invoke(null, mmsi, lat, lon, speed, course, trueHeading, navStatus);
		String result = "000001000001011111011111000001011110000000000000000001001100000000001010010010001111100000000000101001001000111110000000000000000000000000100010000000000000000000000000";

		// Compare binary structure - but ignoring timestamp
		assertEquals(returnValue.substring(0, 137), result.substring(0, 137));
		assertEquals(returnValue.substring(143, 168), result.substring(143, 168));
	}
	
	@Test
	public void AisShipGenerationTest5() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		int mmsi = 100123000;
		double lat = 1.1234;
		double lon = 2.2468;
		
		Method method = AISEncoder.class.getDeclaredMethod("getBinaryStringMsg5", int.class, double.class, double.class, IShip.class, int.class, int.class, int.class, int.class, String.class, int.class);
		method.setAccessible(true);
		String returnValue = (String) method.invoke(null, mmsi, lat, lon, ShipFactory.getShip(1), 1, 2, 3, 4, "DESTINATION", 5);
		String result = "0001010000010111110111110000010111100000000000000011110100001001000001000110110000110000110000110000110000110001000110001001010011001000000101010010010011000110010010001001010100011010000101000000000000000000000000000000000000000000000111100000001010000001110001010001000101000100010000110001000000101000010000010101001101010000100100111000000101010000100100111100111000000000000000000000000000000000000000000000000000000000";
		
		assertEquals(returnValue, result);
	}
	
	
}
