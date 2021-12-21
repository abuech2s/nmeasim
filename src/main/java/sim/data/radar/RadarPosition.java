package sim.data.radar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sim.model.GeoCoordinate;

@AllArgsConstructor
public class RadarPosition {

	@Setter @Getter private GeoCoordinate geoCoordinate;
	@Setter @Getter private String trackName;
	
}
