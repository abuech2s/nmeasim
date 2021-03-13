package sim.data.ais.data.ship;

import lombok.Getter;

public class Cargo2 implements IShip {
	
	@Getter private int imo = 1000005;
	@Getter private String callsign = "C100002";
	@Getter private String vesselName = "Dumbo";
	@Getter int draught = 29;
	@Getter int bow = 18;
	@Getter int stern = 55;
	@Getter int port = 14;
	@Getter int starboard = 24;
	@Getter int shipType = 70;

}
