package sim.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType (XmlAccessType.FIELD)
public class Config {
	
	@XmlAttribute (name = "type")
	private String type = "";
	
	@XmlAttribute (name = "active")
	private String active = "";
	
	@XmlAttribute (name = "port")
	private String port = "";
	
	@XmlAttribute (name = "nroftrack")
	private String nroftrack = "";
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getPort() {
		return Integer.parseInt(port);
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public int getNroftrack() {
		return Integer.parseInt(nroftrack);
	}
	public void setNroftrack(String nroftrack) {
		this.nroftrack = nroftrack;
	}
	
	public boolean getActive() {
		return Boolean.parseBoolean(active);
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "[type=" + getType() + " port=" + getPort() + " active=" + getActive() + " NrOfTracks=" + getNroftrack() + "]";
	}
}
