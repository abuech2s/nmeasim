package simulator;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sim.data.ais.data.ship.IShip;
import sim.data.ais.data.ship.ShipFactory;

public class AisShipGenerationTest {

	@Test
	public void AisShipGenerationTest1() {
		
		for (int i = 0; i < 10; i++) {
			IShip ship = ShipFactory.getRandomShip();
			
			assertTrue(ship.getCallsign() != null);
			assertTrue(!ship.getCallsign().isEmpty());
			
			assertTrue(ship.getVesselName() != null);
			assertTrue(!ship.getVesselName().isEmpty());
			
			assertTrue(ship.getBow() > 0);
			assertTrue(ship.getDraught() > 0);
			assertTrue(ship.getPort() > 0);
			assertTrue(ship.getStarboard() > 0);
			assertTrue(ship.getStern() > 0);
			
			assertTrue(ship.getImo() > 0);
			assertTrue(ship.getShipType() > 0);
		}
	}
	
}
