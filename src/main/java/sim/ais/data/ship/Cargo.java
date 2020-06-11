package sim.ais.data.ship;

public class Cargo implements IShip {

	@Override
	public int getIMO() {
		return 1000003;
	}

	@Override
	public String getCallsign() {
		return "C100001";
	}

	@Override
	public String getVesselName() {
		return "BigCargo";
	}

	@Override
	public int getDraught() {
		return 15;
	}

	@Override
	public int getBow() {
		return 20;
	}

	@Override
	public int getStern() {
		return 50;
	}

	@Override
	public int getPort() {
		return 12;
	}

	@Override
	public int getStarboard() {
		return 25;
	}

	@Override
	public int getShipType() {
		return 70;
	}

}
