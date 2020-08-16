package sim.data.ais.data.ship;

import lombok.Getter;

public class Sailer implements IShip {
	
	@Getter private int imo = 1000002;
	@Getter private String callsign = "S000001";
	@Getter private String vesselName = "Sailermoon";
	@Getter int draught = 3;
	@Getter int bow = 2;
	@Getter int stern = 3;
	@Getter int port = 4;
	@Getter int starboard = 5;
	@Getter int shipType = 36;

}
