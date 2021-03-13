package sim.data.ais.data.ship;

import lombok.Getter;

public class Fisher2 implements IShip {
	
	@Getter private int imo = 1000006;
	@Getter private String callsign = "F000002";
	@Getter private String vesselName = "Sardelle";
	@Getter int draught = 11;
	@Getter int bow = 4;
	@Getter int stern = 6;
	@Getter int port = 5;
	@Getter int starboard = 4;
	@Getter int shipType = 30;

}
