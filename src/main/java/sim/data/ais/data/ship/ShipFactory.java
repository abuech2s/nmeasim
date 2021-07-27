package sim.data.ais.data.ship;

import java.util.concurrent.ThreadLocalRandom;

public class ShipFactory {
	
	private static int max = 7;

	public static IShip getRandomShip() {
		int randomNr = ThreadLocalRandom.current().nextInt(max + 1) + 1;
		return getShip(randomNr);
	}
	
	public static IShip getShip(int type) {
		switch (type) {
		case 1:
			return new Fisher();
		case 2:
			return new Sailer();
		case 3:
			return new Cargo();
		case 4:
			return new Tug();
		case 5:
			return new Cargo2();
		case 6:
			return new Fisher2();
		case 7:
			return new Fisher3();
		default:
			return new Fisher();
		}
	}
	
}
