package sim.data.ais.data.ship;

public class Fisher implements IShip {

	@Override
	public int getIMO() {
		return 1000001;
	}

	@Override
	public String getCallsign() {
		return "F000001";
	}

	@Override
	public String getVesselName() {
		return "FishersFritze";
	}

	@Override
	public int getDraught() {
		return 10;
	}

	@Override
	public int getBow() {
		return 5;
	}

	@Override
	public int getStern() {
		return 7;
	}

	@Override
	public int getPort() {
		return 5;
	}

	@Override
	public int getStarboard() {
		return 4;
	}

	@Override
	public int getShipType() {
		return 30;
	}

}
