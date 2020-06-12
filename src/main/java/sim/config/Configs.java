package sim.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name = "configs")
@XmlAccessorType (XmlAccessType.FIELD)
public class Configs {

	@XmlElement(name = "config")
	private List<Config> configs = null;
	
	public List<Config> getConfigs() {  
	    return configs;  
	}  
	
	public void setAnswers(List<Config> configs) {  
	    this.configs = configs;  
	} 
	
	public Config getConfig(String type) {
		for (Config config : configs) {
			if (config.getType().equalsIgnoreCase(type)) return config;
		}
		return null;
	}
	
}
