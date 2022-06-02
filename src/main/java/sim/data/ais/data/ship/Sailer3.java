package sim.data.ais.data.ship;

import lombok.Getter;

public class Sailer3 implements IShip {
	
	@Getter private int imo = 1000013;
	@Getter private String callsign = "S000003";
	@Getter private String vesselName = "SteveSailor";
	@Getter int draught = 4;
	@Getter int bow = 1;
	@Getter int stern = 2;
	@Getter int port = 6;
	@Getter int starboard = 4;
	@Getter int shipType = 36;

}
