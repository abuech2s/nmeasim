package sim.config;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType (XmlAccessType.FIELD)
public class Config {
	
	@XmlAttribute (name = "type")
	@Getter private String type = "";
	
	@XmlAttribute (name = "sink")
	@Getter private String sink = "";
	
	@XmlAttribute (name = "active")
	@Setter @Getter private boolean active = false;
	
	@XmlAttribute (name = "ip")
	@Getter private String ip = "";
	
	@XmlAttribute (name = "port")
	@Getter private int port = 0;
	
	@XmlAttribute (name = "nroftrack")
	@Getter private int nroftrack = 1;

	
	@Override
	public String toString() {
		return "[ " + getType() + " (" + getIp() + ":" + getPort() + ") sink=" + getSink() + " isActive=" + isActive() + " Nroftracks=" + getNroftrack() + " ]";
	}
}
