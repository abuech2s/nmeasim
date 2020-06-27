package sim.data.ais.data.seagraphs;

import sim.data.ais.Connection;
import sim.model.GeoCoordinate;

public class NorthSea extends SeaGraph {

	@Override
	protected void init() {

		habours.add(new GeoCoordinate("cuxhaven",53.8731349,8.7131946, true));
		habours.add(new GeoCoordinate("buesum",54.1284081,8.8515602, true));
		habours.add(new GeoCoordinate("sanktpeterording",54.2942473,8.6201607, true));
		habours.add(new GeoCoordinate("helgoland",54.1798707,7.8916387, true));
		habours.add(new GeoCoordinate("wangerooge",53.7861597,7.8743442, true));
		habours.add(new GeoCoordinate("spiekerooge",53.7627257,7.6946148, true));
		habours.add(new GeoCoordinate("langerooge",53.7276637,7.4963619, true));
		habours.add(new GeoCoordinate("baltrum",53.7258942,7.3612792, true));
		habours.add(new GeoCoordinate("norderney",53.6981077,7.1650233, true));
		habours.add(new GeoCoordinate("juist",53.6734351,7.0001038, true));
		habours.add(new GeoCoordinate("norddeich",53.6298475,7.1575872, true));
		habours.add(new GeoCoordinate("dornumersiel",53.6821377,7.4863978, true));
		habours.add(new GeoCoordinate("bensersiel",53.6800879,7.5710329, true));
		habours.add(new GeoCoordinate("neuharlingersiel",53.7036697,7.7049972, true));
		habours.add(new GeoCoordinate("harlesiel",53.7109821,7.8107124, true));
		habours.add(new GeoCoordinate("schillig",53.7011878,8.0309291, true));
		habours.add(new GeoCoordinate("wilhelmshaven",53.5133104,8.1434555, true));
		habours.add(new GeoCoordinate("A",53.4982005,8.1970139));
		habours.add(new GeoCoordinate("B",53.6903922,8.2066269));
		habours.add(new GeoCoordinate("C",54.0551436,8.5252304));
		habours.add(new GeoCoordinate("D",54.1961328,8.3484391));
		habours.add(new GeoCoordinate("E",53.914735,7.5422124));
		habours.add(new GeoCoordinate("F",53.7608933,7.0843762));
		habours.add(new GeoCoordinate("G",53.8103857,7.4098462));
		habours.add(new GeoCoordinate("H",53.8487234,7.7521498));
		habours.add(new GeoCoordinate("I",53.6659318,7.1314222));
		habours.add(new GeoCoordinate("J",53.7059766,7.3549072));
		habours.add(new GeoCoordinate("K",53.7068074,7.5277316));
		habours.add(new GeoCoordinate("L",53.734233,7.7494365));
		habours.add(new GeoCoordinate("M",53.7460104,7.9197246));
		habours.add(new GeoCoordinate("N",53.983458,8.1505824));
		habours.add(new GeoCoordinate("hamburg",53.5402585,9.9116782, true));
		habours.add(new GeoCoordinate("O",53.5443387,9.8584632));
		habours.add(new GeoCoordinate("P",53.5597381,9.757183));
		habours.add(new GeoCoordinate("Q",53.5719684,9.6557496));
		habours.add(new GeoCoordinate("R",53.6203596,9.5453713));
		habours.add(new GeoCoordinate("S",53.713446,9.4790377));
		habours.add(new GeoCoordinate("T",53.7587323,9.4059099));
		habours.add(new GeoCoordinate("U",53.8594862,9.3001665));
		habours.add(new GeoCoordinate("V",53.8803365,9.0976061));
		habours.add(new GeoCoordinate("W",53.9046056,8.7240366));
		habours.add(new GeoCoordinate("bremerhaven",53.537562,8.573317, true));
		habours.add(new GeoCoordinate("X",53.678519,8.3535904));

		connections.add(new Connection("juist", "I", 13.7));
		connections.add(new Connection("norddeich", "I", 4.3));
		connections.add(new Connection("nordeney", "I", 4.2));
		connections.add(new Connection("I", "F", 11.0));
		connections.add(new Connection("I", "J", 15.4));
		connections.add(new Connection("F", "G", 22.1));
		connections.add(new Connection("F", "baltrum", 2.2));
		connections.add(new Connection("G", "Z", 10.4));
		connections.add(new Connection("G", "F", 22.0));
		connections.add(new Connection("J", "Z", 6.4));
		connections.add(new Connection("J", "K", 11.4));
		connections.add(new Connection("Z", "K", 5.3));
		connections.add(new Connection("K", "langerooge", 3.11));
		connections.add(new Connection("K", "dornumersiel", 3.8));
		connections.add(new Connection("K", "bensersiel", 4.1));
		connections.add(new Connection("K", "Y", 9.76));
		connections.add(new Connection("K", "L", 14.9));
		connections.add(new Connection("L", "Y", 6.21));
		connections.add(new Connection("L", "spiekerooge", 4.8));
		connections.add(new Connection("L", "harlesiel", 4.79));
		connections.add(new Connection("neuharlingersiel", "L", 4.49));
		connections.add(new Connection("L", "M", 11.3));
		connections.add(new Connection("M", "wangerooge", 5.3));
		connections.add(new Connection("AB", "M", 8.15));
		connections.add(new Connection("AB", "schillig", 7.54));
		connections.add(new Connection("AB", "H", 20.8));
		connections.add(new Connection("E", "H", 15.6));
		connections.add(new Connection("E", "Y", 20.0));
		
		connections.add(new Connection("wilhelmshaven", "A", 3.92));
		connections.add(new Connection("A", "B", 21.4));
		connections.add(new Connection("B", "schillig", 11.6));
		connections.add(new Connection("B", "X", 9.78));
		connections.add(new Connection("B", "M", 19.9));
		connections.add(new Connection("B", "N", 32.8));
		connections.add(new Connection("X", "N", 36.5));
		connections.add(new Connection("X", "bremerhaven", 21.4));
		connections.add(new Connection("X", "AA", 25.2));
		connections.add(new Connection("AB", "AA", 33.3));
		connections.add(new Connection("N", "AA", 25.4));
		connections.add(new Connection("N", "C", 19.5));
		connections.add(new Connection("N", "D", 27.0));
		connections.add(new Connection("N", "H", 30.1));
		connections.add(new Connection("N", "helgoland", 27.6));
		connections.add(new Connection("H", "helgoland", 38.0));
		connections.add(new Connection("E", "helgoland", 37.3));
		connections.add(new Connection("D", "helgoland", 29.8));
		connections.add(new Connection("D", "sanktpeterording", 20.8));
		connections.add(new Connection("C", "sanktpeterording", 27.3));
		connections.add(new Connection("C", "D", 19.5));
		connections.add(new Connection("buesum", "C", 22.8));
		connections.add(new Connection("buesum", "W", 26.3));
		connections.add(new Connection("C", "W", 21.2));
		connections.add(new Connection("AA", "W", 14.7));
		connections.add(new Connection("cuxhaven", "W", 3.57));
		
		connections.add(new Connection("hamburg", "O", 3.55));
		connections.add(new Connection("O", "P", 6.91));
		connections.add(new Connection("P", "Q", 6.84));
		connections.add(new Connection("Q", "R", 9.07));
		connections.add(new Connection("R", "S", 11.2));
		connections.add(new Connection("S", "T", 6.97));
		connections.add(new Connection("T", "U", 13.2));
		connections.add(new Connection("U", "V", 13.5));
		connections.add(new Connection("cuxhaven", "V", 25.2));
	}

}
