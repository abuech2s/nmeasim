package sim.data.ais.data.ship;

import lombok.Getter;

public class Sailer2 implements IShip {
	
	@Getter private int imo = 1000011;
	@Getter private String callsign = "S000002";
	@Getter private String vesselName = "DrunkenSailor";
	@Getter int draught = 2;
	@Getter int bow = 3;
	@Getter int stern = 2;
	@Getter int port = 5;
	@Getter int starboard = 4;
	@Getter int shipType = 36;

}
