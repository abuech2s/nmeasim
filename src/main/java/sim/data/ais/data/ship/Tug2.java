package sim.data.ais.data.ship;

import lombok.Getter;

public class Tug2 implements IShip {

	@Getter private int imo = 1000009;
	@Getter private String callsign = "T100002";
	@Getter private String vesselName = "BigContainer";
	@Getter int draught = 7;
	@Getter int bow = 8;
	@Getter int stern = 12;
	@Getter int port = 8;
	@Getter int starboard = 9;
	@Getter int shipType = 31;
	
}
