package sim.data.ais.data.ship;

import lombok.Getter;

public class Fisher implements IShip {
	
	@Getter private int imo = 1000001;
	@Getter private String callsign = "F000001";
	@Getter private String vesselName = "FishersFritze";
	@Getter int draught = 10;
	@Getter int bow = 5;
	@Getter int stern = 7;
	@Getter int port = 5;
	@Getter int starboard = 4;
	@Getter int shipType = 30;

}
