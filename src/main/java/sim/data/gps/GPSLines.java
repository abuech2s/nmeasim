package sim.data.gps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import sim.model.GeoCoordinate;

public class GPSLines {
	private static Map<String, List<GeoCoordinate>> gpsLines = null;
	
	private static void init() {
		gpsLines = new HashMap<>();
		
		List<GeoCoordinate> kiel_rodbyhavn = new ArrayList<>();
		kiel_rodbyhavn.add(new GeoCoordinate(54.318396, 10.141522));
		kiel_rodbyhavn.add(new GeoCoordinate(54.389060, 10.199262));
		kiel_rodbyhavn.add(new GeoCoordinate(54.454903, 10.239679));
		kiel_rodbyhavn.add(new GeoCoordinate(54.653804, 11.348283));
		//gpsLines.put("kiel_rodbyhavn", kiel_rodbyhavn);
		
		List<GeoCoordinate> kiel_eckernfoerde = new ArrayList<>();
		kiel_eckernfoerde.add(new GeoCoordinate(54.318396, 10.141522));
		kiel_eckernfoerde.add(new GeoCoordinate(54.389060, 10.199262));
		kiel_eckernfoerde.add(new GeoCoordinate(54.454903, 10.239679));
		kiel_eckernfoerde.add(new GeoCoordinate(54.518510, 10.070901));
		kiel_eckernfoerde.add(new GeoCoordinate(54.464082, 9.851926));
		kiel_eckernfoerde.add(new GeoCoordinate(54.476031, 9.839995));
		//gpsLines.put("kiel_eckernfoerde", kiel_eckernfoerde);
		
		
		List<GeoCoordinate> flensburg_luebeck = new ArrayList<>();
		flensburg_luebeck.add(new GeoCoordinate(54.7895323, 9.4364189));
		flensburg_luebeck.add(new GeoCoordinate(54.8016057, 9.4336723));
		flensburg_luebeck.add(new GeoCoordinate(54.8287081, 9.4618248));
		flensburg_luebeck.add(new GeoCoordinate(54.8895728, 9.5974373));
		flensburg_luebeck.add(new GeoCoordinate(54.8733775, 9.6341728));
		flensburg_luebeck.add(new GeoCoordinate(54.8423514, 9.6091103));
		flensburg_luebeck.add(new GeoCoordinate(54.8069484, 9.812014));
		flensburg_luebeck.add(new GeoCoordinate(54.8182254, 10.0210976));
		flensburg_luebeck.add(new GeoCoordinate(54.3510846, 10.7255959));
		flensburg_luebeck.add(new GeoCoordinate(54.5506922, 11.0263466));
		flensburg_luebeck.add(new GeoCoordinate(54.6541038, 11.3463234));
		flensburg_luebeck.add(new GeoCoordinate(54.4366358, 10.471537));
		flensburg_luebeck.add(new GeoCoordinate(54.8023972, 9.9936318));
		flensburg_luebeck.add(new GeoCoordinate(54.3814877, 12.2993874));
		flensburg_luebeck.add(new GeoCoordinate(54.0716084, 11.0414528));
		flensburg_luebeck.add(new GeoCoordinate(53.9595628, 10.8857998));
		flensburg_luebeck.add(new GeoCoordinate(53.9526944, 10.8590206));
		flensburg_luebeck.add(new GeoCoordinate(53.9387521, 10.8652005));
		flensburg_luebeck.add(new GeoCoordinate(53.9173243, 10.8717236));
		flensburg_luebeck.add(new GeoCoordinate(53.8906253, 10.8092388));
		flensburg_luebeck.add(new GeoCoordinate(53.8881972, 10.8037457));
		//gpsLines.put("flensburg_luebeck", flensburg_luebeck);
		
		List<GeoCoordinate> nordseeinseln = new ArrayList<>();
		nordseeinseln.add(new GeoCoordinate(53.5151881, 8.1399453));
		nordseeinseln.add(new GeoCoordinate(53.4923179, 8.1797708));
		nordseeinseln.add(new GeoCoordinate(53.5437585, 8.1989969));
		nordseeinseln.add(new GeoCoordinate(53.6733071, 8.2443155));
		nordseeinseln.add(new GeoCoordinate(53.7683817, 8.2058633));
		nordseeinseln.add(new GeoCoordinate(53.8332663, 7.9930032));
		nordseeinseln.add(new GeoCoordinate(53.6602888, 6.5730203));
		nordseeinseln.add(new GeoCoordinate(53.5690476, 6.6087259));
		nordseeinseln.add(new GeoCoordinate(53.5274349, 6.7611612));
		nordseeinseln.add(new GeoCoordinate(53.6448242, 7.0674051));
		nordseeinseln.add(new GeoCoordinate(53.6903876, 7.2775186));
		nordseeinseln.add(new GeoCoordinate(53.7066483, 7.4725259));
		nordseeinseln.add(new GeoCoordinate(53.7342771, 7.7856363));
		nordseeinseln.add(new GeoCoordinate(53.7310276, 8.0328287));
		nordseeinseln.add(new GeoCoordinate(53.6269109, 8.1770242));
		nordseeinseln.add(new GeoCoordinate(53.5404943, 8.1811441));
		nordseeinseln.add(new GeoCoordinate(53.5115134, 8.1550516));
		nordseeinseln.add(new GeoCoordinate(53.5151881, 8.1399453));
		//gpsLines.put("nordseeinseln", nordseeinseln);
		
		List<GeoCoordinate> kiel_danzig = new ArrayList<>();
		kiel_danzig.add(new GeoCoordinate(54.3464143, 10.1325133));
		kiel_danzig.add(new GeoCoordinate(54.4774815, 10.2918150));
		kiel_danzig.add(new GeoCoordinate(54.5540095, 11.1981871));
		kiel_danzig.add(new GeoCoordinate(54.4710977, 12.0551207));
		kiel_danzig.add(new GeoCoordinate(54.7795662, 13.5053160));
		kiel_danzig.add(new GeoCoordinate(54.4870554, 14.0601256));
		kiel_danzig.add(new GeoCoordinate(54.2470363, 13.8898375));
		kiel_danzig.add(new GeoCoordinate(54.076576, 14.3512633));
		kiel_danzig.add(new GeoCoordinate(55.0573939, 18.2678892));
		kiel_danzig.add(new GeoCoordinate(54.7161558, 18.9490416));
		kiel_danzig.add(new GeoCoordinate(54.4902461, 18.9600279));
		kiel_danzig.add(new GeoCoordinate(54.4072048, 18.6908629));
		//gpsLines.put("kiel_danzig", kiel_danzig);
		
		List<GeoCoordinate> balticSea = new ArrayList<>();
		balticSea.add(new GeoCoordinate(54.6746753,10.0616863));
		balticSea.add(new GeoCoordinate(54.5123633,10.1550701));
		balticSea.add(new GeoCoordinate(54.3910041,10.8087566));
		balticSea.add(new GeoCoordinate(54.6429004,10.9680584));
		balticSea.add(new GeoCoordinate(54.2676809,12.0529583));
		balticSea.add(new GeoCoordinate(54.8568991,13.0774334));
		balticSea.add(new GeoCoordinate(54.6039002,14.4280154));
		balticSea.add(new GeoCoordinate(54.4253303,15.3014285));
		balticSea.add(new GeoCoordinate(55.0217328,16.4385135));
		balticSea.add(new GeoCoordinate(55.3916002,14.8619754));
		balticSea.add(new GeoCoordinate(55.2352962,13.8677127));
		balticSea.add(new GeoCoordinate(55.2352963,12.8020389));
		balticSea.add(new GeoCoordinate(54.7531695,12.6207644));
		balticSea.add(new GeoCoordinate(54.4492912,11.7198855));
		balticSea.add(new GeoCoordinate(54.6960674,11.1595828));
		balticSea.add(new GeoCoordinate(54.6746753,10.0616863));
		gpsLines.put("balticsea", balticSea);
	}
	
	public static List<GeoCoordinate> get(String line) {
		if (gpsLines == null) init();
		line = line.toLowerCase().trim();
		return gpsLines.get(line);
	}
	
	@SuppressWarnings("unchecked")
	public static List<GeoCoordinate> getRandom() {
		if (gpsLines == null) init();
		Random rand = new Random();
		Object[] values = gpsLines.values().toArray();
		Object o = values[rand.nextInt(values.length)];
		return (List<GeoCoordinate>)o;
	}
}
