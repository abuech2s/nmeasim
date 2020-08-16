package sim.config;

public class Constants {

	//Identifiers
	public static final String TOKEN_ADSB = "adsb";
	public static final String TOKEN_AIS = "ais";
	public static final String TOKEN_GPS = "gps";
	public static final String TOKEN_RADAR = "radar";
	
	//Config parameters
	public static final String CONFIG_FILENAME = "config.xml";
	public static final int CONFIG_RELOADTIME = 15_000;
	
	//Track parameters
	public static final long TRACK_SLEEP_TIME = 600 * 1_000L; // in [s]
	
	//Physical constants
	public static final double fromMtoNM = 0.000539957; // from [m] to [NM]
	
	//Filenames
	public static final String adsbCities = "cities.csv";
}
