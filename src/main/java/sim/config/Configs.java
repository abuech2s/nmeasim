package sim.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name = "configs")
@XmlAccessorType (XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class Configs {

	@XmlElement(name = "config")
	@Getter private List<Config> configs = null; 
	
	public Config getConfig(String type) {
		for (Config config : configs) {
			if (config.getType().equalsIgnoreCase(type)) return config;
		}
		return null;
	}
	
}
