package sim.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.App;
import sim.model.tracks.TrackAdministration;

public class Configuration implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);
	
	private static Configs configs = null;
	private MessageDigest md5Digest = null;
	private File file = null;
	private JAXBContext jaxbContext = null;
	private Unmarshaller jaxbUnmarshaller = null;
	private String lastChecksum = "";

	private void init() throws JAXBException, NoSuchAlgorithmException, NoSuchFileException {
		if (md5Digest == null) {
			md5Digest = MessageDigest.getInstance("MD5");
			file = new File(Constants.CONFIG_FILENAME);
			
			if (!file.exists()) {
				throw new NoSuchFileException("File " + Constants.CONFIG_FILENAME + " does not exist.");
			}
			
			jaxbContext = JAXBContext.newInstance(Configs.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		}
		
		try {
			String currentChecksum = getFileChecksum(md5Digest, file);
			if (lastChecksum.isEmpty() || !currentChecksum.equals(lastChecksum)) {
		        configs = (Configs) jaxbUnmarshaller.unmarshal(file);
		        log.info("(Re)load config file.");
		        validate(configs);
		        printConfig();
		        TrackAdministration.reInit(configs);
		        lastChecksum = currentChecksum;
			}
		} catch (IOException e) {
			log.warn("Could not calculate hash from config file.", e);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				init();
			} catch (NoSuchFileException e) {
				log.warn("File not found: {}", e);
				break;
			} catch (Exception e) {
				log.warn("Exception while loading config file {}", e);
			}
			try { Thread.sleep(Constants.CONFIG_RELOADTIME); } catch (Exception e) {}
		}
	}
	
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException
	{
	    FileInputStream fis = new FileInputStream(file);
	     
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0; 
	  
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };

	    fis.close();

	    byte[] bytes = digest.digest();

	    StringBuilder sb = new StringBuilder();
	    for (int i=0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }

	   return sb.toString();
	}
	
	private void printConfig() {
		for (Config config : configs.getConfigs()) {
			log.info("    {}", config.toString());
		}
	}
	
	private boolean validate(Configs configs) {
		//Check for additionally needed GPS support:
		for (Config c : configs.getConfigs()) {
			if (c.isActive() && (c.getType().equalsIgnoreCase(Constants.TOKEN_RADAR) || c.getType().equalsIgnoreCase(Constants.TOKEN_WEATHER))) {
				configs.getConfig(Constants.TOKEN_GPS).setActive(true);
			}
		}
		
		//Check if ports exist multiple times:
		Set<Integer> ports = new HashSet<>();
		for (Config c : configs.getConfigs()) {
			ports.add(c.getPort());
		}
		if (ports.size() != configs.getConfigs().size()) App.exit("At least two configs have the same port.");

		return true;
	}
	
}
