package sim.data.ais.data.ship;

import lombok.Getter;

public class Passenger2 implements IShip {
	
	@Getter private int imo = 1000015;
	@Getter private String callsign = "P100002";
	@Getter private String vesselName = "FerryTail";
	@Getter int draught = 12;
	@Getter int bow = 5;
	@Getter int stern = 5;
	@Getter int port = 10;
	@Getter int starboard = 12;
	@Getter int shipType = 60;

}
