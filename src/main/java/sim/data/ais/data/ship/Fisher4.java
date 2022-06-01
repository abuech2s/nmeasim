package sim.data.ais.data.ship;

import lombok.Getter;

public class Fisher4 implements IShip {
	
	@Getter private int imo = 1000008;
	@Getter private String callsign = "F000004";
	@Getter private String vesselName = "Fishly";
	@Getter int draught = 5;
	@Getter int bow = 2;
	@Getter int stern = 6;
	@Getter int port = 4;
	@Getter int starboard = 4;
	@Getter int shipType = 30;

}
