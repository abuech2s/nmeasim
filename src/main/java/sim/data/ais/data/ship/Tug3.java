package sim.data.ais.data.ship;

import lombok.Getter;

public class Tug3 implements IShip {

	@Getter private int imo = 1000010;
	@Getter private String callsign = "T100003";
	@Getter private String vesselName = "SeaFlorian";
	@Getter int draught = 10;
	@Getter int bow = 8;
	@Getter int stern = 20;
	@Getter int port = 12;
	@Getter int starboard = 4;
	@Getter int shipType = 31;
	
}
