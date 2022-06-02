package sim.data.ais.data.ship;

import lombok.Getter;

public class Passenger implements IShip {
	
	@Getter private int imo = 1000014;
	@Getter private String callsign = "P100002";
	@Getter private String vesselName = "Titanic";
	@Getter int draught = 25;
	@Getter int bow = 13;
	@Getter int stern = 15;
	@Getter int port = 40;
	@Getter int starboard = 65;
	@Getter int shipType = 60;

}
