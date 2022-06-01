package sim.data.ais.data.ship;

import lombok.Getter;

public class Cargo3 implements IShip {
	
	@Getter private int imo = 1000012;
	@Getter private String callsign = "C100003";
	@Getter private String vesselName = "Dickie";
	@Getter int draught = 35;
	@Getter int bow = 16;
	@Getter int stern = 18;
	@Getter int port = 20;
	@Getter int starboard = 22;
	@Getter int shipType = 70;

}
