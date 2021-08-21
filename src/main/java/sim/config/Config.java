package sim.config;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType (XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config {
	
	@XmlAttribute (name = "type")
	@Getter private String type;
	
	@XmlAttribute (name = "sink")
	@Getter private String sink;
	
	@XmlAttribute (name = "active")
	@Setter @Getter private boolean active = false;
	
	@XmlAttribute (name = "ip")
	@Getter private String ip;
	
	@XmlAttribute (name = "port")
	@Getter private int port;
	
	@XmlAttribute (name = "nroftrack")
	@Getter private int nroftrack = 1;
	
	@XmlAttribute (name = "streamsleeptime")
	private long streamsleeptime;
	
	@ToString.Exclude @Getter private int amount;
	
	public long getStreamSleepTime() {
		if (streamsleeptime == 0) return Constants.TRACK_SLEEP_TIME;
		return streamsleeptime;
	}
}
