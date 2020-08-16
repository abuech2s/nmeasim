package sim.data.ais.data.ship;

import java.util.concurrent.ThreadLocalRandom;

public class ShipFactory {
	
	private static int max = 3;

	public static IShip getRandomShip() {
		int randomNr = ThreadLocalRandom.current().nextInt(max + 1);
		switch (randomNr) {
		case 0: 
			return new Fisher();
		case 1:
			return new Cargo();
		case 2:
			return new Sailer();
		default: 
			return new Fisher();
		}
	}
	
}
