package sim.config;

public class Constants {

	public static final String TOKEN_ADSB = "adsb";
	public static final String TOKEN_AIS = "ais";
	public static final String TOKEN_GPS = "gps";
	public static final String TOKEN_RADAR = "radar";
	
	public static final String configFileName = "config.xml";
	public static final int configReloadTime = 15_000;
	
	public static final long TRACK_SLEEP_TIME = 600 * 1_000L; // in [s]
	
	public static final double fromMtoNM = 0.000539957; // from [m] to [NM]
}
