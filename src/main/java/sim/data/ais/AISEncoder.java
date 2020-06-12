package sim.data.ais;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.data.ais.data.ship.IShip;
import sim.model.GeoOps;

public class AISEncoder {
	
	private static final Logger log = LoggerFactory.getLogger(AISEncoder.class);
	
    private static Map<String, String> encodingAlphabet = new HashMap<String, String>()
    {
		private static final long serialVersionUID = 1L;
	{
		put("000000", "0");
		put("000001", "1");
		put("000010", "2");
		put("000011", "3");
		put("000100", "4");
		put("000101", "5");
		put("000110", "6");
		put("000111", "7");
		put("001000", "8");
		put("001001", "9");
		put("001010", ":");
		put("001011", ";");
		put("001100", "<");
		put("001101", "=");
		put("001110", ">");
		put("001111", "?");
		put("010000", "@");
		put("010001", "A");
		put("010010", "B");
		put("010011", "C");
		put("010100", "D");
		put("010101", "E");
		put("010110", "F");
		put("010111", "G");
		put("011000", "H");
		put("011001", "I");
		put("011010", "J");
		put("011011", "K");
		put("011100", "L");
		put("011101", "M");
		put("011110", "N");
		put("011111", "O");
		put("100000", "P");
		put("100001", "Q");
		put("100010", "R");
		put("100011", "S");
		put("100100", "T");
		put("100101", "U");
		put("100110", "V");
		put("100111", "W");
		put("101000", "`");
		put("101001", "a");
		put("101010", "b");
		put("101011", "c");
		put("101100", "d");
		put("101101", "e");
		put("101110", "f");
		put("101111", "g");
		put("110000", "h");
		put("110001", "i");
		put("110010", "j");
		put("110011", "k");
		put("110100", "l");
		put("110101", "m");
		put("110110", "n");
		put("110111", "o");
		put("111000", "p");
		put("111001", "q");
		put("111010", "r");
		put("111011", "s");
		put("111100", "t");
		put("111101", "u");
		put("111110", "v");
		put("111111", "w");
    }};
    
    private static Map<String, String> asciiTable = new HashMap<String, String>()
    {
		private static final long serialVersionUID = 1L;
	{
		put("000000", "@");
		put("000001", "A");
		put("000010", "B");
		put("000011", "C");
		put("000100", "D");
		put("000101", "E");
		put("000110", "F");
		put("000111", "G");
		put("001000", "H");
		put("001001", "I");
		put("001010", "J");
		put("001011", "K");
		put("001100", "L");
		put("001101", "M");
		put("001110", "N");
		put("001111", "O");
		put("010000", "P");
		put("010001", "Q");
		put("010010", "R");
		put("010011", "S");
		put("010100", "T");
		put("010101", "U");
		put("010110", "V");
		put("010111", "W");
		put("011000", "X");
		put("011001", "Y");
		put("011010", "Z");
		put("011011", "[");
		put("011100", "\\");
		put("011101", "]");
		put("011110", "^");
		put("011111", "_");
		put("100000", " ");
		put("100001", "!");
		put("100010", "\"");
		put("100011", "#");
		put("100100", "$");
		put("100101", "%");
		put("100110", "&");
		put("100111", "'");
		put("101000", "(");
		put("101001", ")");
		put("101010", "*");
		put("101011", "+");
		put("101100", ",");
		put("101101", "-");
		put("101110", ".");
		put("101111", "/");
		put("110000", "0");
		put("110001", "1");
		put("110010", "2");
		put("110011", "3");
		put("110100", "4");
		put("110101", "5");
		put("110110", "6");
		put("110111", "7");
		put("111000", "8");
		put("111001", "9");
		put("111010", ":");
		put("111011", ";");
		put("111100", "<");
		put("111101", "=");
		put("111110", ">");
		put("111111", "?");
    }};

	public static String encode(String binaryString) {
		String compressedString = "";
		for (int i = 0; i < binaryString.length(); i = i + 6) {
			String subString = binaryString.substring(i, i+6);
			compressedString += encodingAlphabet.get(subString);
		}
		return compressedString;
	}
	
	private static String getBinValue(char c) {
		for (Entry<String, String> e : asciiTable.entrySet()) {
			if (String.valueOf(c).equals(e.getValue())) {
				return e.getKey();
			}
		}
		return "";
	}
	
	private static String getBinString(String value, int totalLength) {
		value = value.toUpperCase();
		if (value.length() > 20) value = value.substring(0, 20);
		String binMessage = "";
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			binMessage += getBinValue(c);
		}
		binMessage = StringUtils.rightPad(binMessage, totalLength, "000000"); // 000000 = @
		return binMessage;
	}
	
	private static String DecToBin(int decValue, int totalLength) {
		String strValue = Integer.toBinaryString(decValue);
		strValue = StringUtils.leftPad(strValue, totalLength, "0");
		return strValue;
	}
	
	public static String getBinaryStringMsg1(int mmsi, double lat, double lon, double speed) {
		String binaryString = "";
		
		//168 bits
		binaryString += "000001";								// message id			 6
		binaryString += "00"; 									// repeat indicator		 2
		binaryString += DecToBin(mmsi, 30);						// mmsi 				30
		binaryString += "0000";									// nav status			 4
		binaryString += "00000000";								// ROT					 8
		binaryString += DecToBin((int)(speed*10.0), 10);		// SOG					10
		binaryString += "0";									// Pos Acc				 1
		binaryString += DecToBin((int)(lon * 600000.0), 28);	// lon					28					
		binaryString += DecToBin((int)(lat * 600000.0), 27);	// lat					27
		binaryString += "000000000000";							// COG					12
		binaryString += "000000000";							// True Heading			 9
		binaryString += DecToBin(Calendar.getInstance(TimeZone.getTimeZone("UTC")).get(Calendar.SECOND), 6);	// UTC Sec			6
		binaryString += "00";									// SMI					 2
		binaryString += "000";									// spare				 3
		binaryString += "0";									// RAIM flag			 1
		binaryString += "0000000000000000000"; 					// Comm state			19
			
		if (binaryString.length() != 168) {
			log.warn("Message has not 168 bits: ");
		}

		return binaryString;
	}
	
	public static String getBinaryStringMsg5(int mmsi, double lat, double lon, IShip ship,
			int etaMonth, int etaDay, int etaHour, int etaMinute, String dest) {
		String binaryString = "";
		
		//424 bits
		binaryString += "000101";								// Message id			  6
		binaryString += "00"; 									// Repeat indicator		  2
		binaryString += DecToBin(mmsi, 30);						// Mmsi 				 30
		binaryString += "00";									// Ais version			  2
		binaryString += DecToBin(ship.getIMO(), 30);			// Imo					 30
		binaryString += DecToBin(0, 120);						// ShipName 			120
		binaryString += getBinString(ship.getCallsign(), 42);	// Callsign				 42
		binaryString += DecToBin(ship.getShipType(), 8);		// ShipType				  8
		binaryString += DecToBin(ship.getBow(), 9);				// Dim bow  			  9
		binaryString += DecToBin(ship.getStern(), 9);			// Dim stern 			  9
		binaryString += DecToBin(ship.getPort(), 6);			// Dim port				  6
		binaryString += DecToBin(ship.getStarboard(), 6);		// Dim starboard		  6
		binaryString += "0000";									// Position Fix Type	  4
		binaryString += DecToBin(etaMonth, 4);					// ETA month			  4
		binaryString += DecToBin(etaDay, 5);					// ETA day  			  5
		binaryString += DecToBin(etaHour, 5);					// ETA hour				  5
		binaryString += DecToBin(etaMinute, 6);					// ETA minutes			  6
		binaryString += DecToBin(ship.getDraught(), 8);			// Draught				  8
		binaryString += getBinString(dest, 120);				// Dest					120
		binaryString += "0";									// DTE					  1
		binaryString += "0";									// Spare				  1
		
		if (binaryString.length() != 424) {
			log.warn("Message has not 424 bits: ");
		}

		return binaryString;
	}
	
	protected static List<String> getFinalAISMessages(String binaryMessage) {
		switch (binaryMessage.substring(0, 6)) {
		case "000001":
			String checksumExprMsg1 = "AIVDM,1,1,,A," + encode(binaryMessage) + ",0";
			String aisMsg1 = "!" + checksumExprMsg1 + "*" + GeoOps.calcCheckSum(checksumExprMsg1);
			return Arrays.asList(aisMsg1);
		case "000101":
			String payload = encode(binaryMessage+"00");
			String checksumExprMsg5a = "AIVDM,2,1,1,A," + payload.substring(0, 60) + ",0";
			String checksumExprMsg5b = "AIVDM,2,2,1,A," + payload.substring(60) + ",2";
			String aisMsg5a = "!" + checksumExprMsg5a + "*" + GeoOps.calcCheckSum(checksumExprMsg5a);
			String aisMsg5b = "!" + checksumExprMsg5b + "*" + GeoOps.calcCheckSum(checksumExprMsg5b);
			return Arrays.asList(aisMsg5a, aisMsg5b);
		}
		return null;
	}
	
}
