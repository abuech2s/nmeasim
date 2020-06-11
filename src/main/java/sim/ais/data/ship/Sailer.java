package sim.ais.data.ship;

public class Sailer implements IShip {

	@Override
	public int getIMO() {
		return 1000002;
	}

	@Override
	public String getCallsign() {
		return "S000001";
	}

	@Override
	public String getVesselName() {
		return "Sailermoon";
	}

	@Override
	public int getDraught() {
		return 3;
	}

	@Override
	public int getBow() {
		return 2;
	}

	@Override
	public int getStern() {
		return 3;
	}

	@Override
	public int getPort() {
		return 4;
	}

	@Override
	public int getStarboard() {
		return 5;
	}

	@Override
	public int getShipType() {
		return 36;
	}

}
