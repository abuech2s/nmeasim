package sim.data.ais.data.ship;

import lombok.Getter;

public class Cargo implements IShip {
	
	@Getter private int imo = 1000003;
	@Getter private String callsign = "C100001";
	@Getter private String vesselName = "BigCargo";
	@Getter int draught = 15;
	@Getter int bow = 20;
	@Getter int stern = 50;
	@Getter int port = 12;
	@Getter int starboard = 25;
	@Getter int shipType = 70;

}
