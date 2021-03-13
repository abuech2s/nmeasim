package sim.data.ais.data.ship;

import lombok.Getter;

public class Fisher3 implements IShip {
	
	@Getter private int imo = 1000007;
	@Getter private String callsign = "F000003";
	@Getter private String vesselName = "Tim-Thuna";
	@Getter int draught = 5;
	@Getter int bow = 3;
	@Getter int stern = 7;
	@Getter int port = 5;
	@Getter int starboard = 6;
	@Getter int shipType = 30;

}
