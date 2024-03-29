package sim.config;

public class Constants {

	//Identifiers
	public static final String TOKEN_ADSB = "adsb";
	public static final String TOKEN_AIS = "ais";
	public static final String TOKEN_GPS = "gps";
	public static final String TOKEN_RADAR = "radar";
	public static final String TOKEN_WEATHER = "weather";
	public static final String TOKEN_COURSE = "course";
	
	//Technical identifiers
	public static final String TOKEN_TCP = "tcp";
	public static final String TOKEN_UDP = "udp";
	
	//Config parameters
	public static final String CONFIG_FILENAME = "config.xml";
	public static final int CONFIG_RELOADTIME = 15_000;
	
	//Track parameters
	public static final long DEFAULT_TRACK_SLEEP_TIME = 600 * 1_000L; // in [s]
	public static final double MAX_RADAR_RADIUS = 30_000.0;
	
	//Physical constants
	public static final double fromMtoNM = 0.000539957; // from [m] to [NM]
	public static final double fromKntoMs = 0.514444; // from [kn] to [m/s]
	public static final double fromMstoKn = 1.94384; // from [m/s] to [kn]
	public static final double fromMmHgtoBar = 0.0013332; // from [mmHg] to [Bar]
	
	//Filenames
	public static final String FILE_ADSBCITIES = "cities.csv";
	public static final String FILE_VERSION = "version.txt";
	
	//Linebreak
	public static final String RCLF = "\r\n";
}
