package sim.data.ais.data.ship;

import lombok.Getter;

public class Tug implements IShip {

	@Getter private int imo = 1000004;
	@Getter private String callsign = "T100001";
	@Getter private String vesselName = "Tuggy";
	@Getter int draught = 8;
	@Getter int bow = 7;
	@Getter int stern = 10;
	@Getter int port = 6;
	@Getter int starboard = 13;
	@Getter int shipType = 31;
	
}
