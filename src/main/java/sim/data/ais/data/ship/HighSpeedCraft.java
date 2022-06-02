package sim.data.ais.data.ship;

import lombok.Getter;

public class HighSpeedCraft implements IShip {
	
	@Getter private int imo = 1000016;
	@Getter private String callsign = "H100001";
	@Getter private String vesselName = "FlotteKarotte";
	@Getter int draught = 1;
	@Getter int bow = 3;
	@Getter int stern = 3;
	@Getter int port = 2;
	@Getter int starboard = 4;
	@Getter int shipType = 40;

}
