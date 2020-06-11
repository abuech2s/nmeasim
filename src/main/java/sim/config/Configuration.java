package sim.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);
	
	private static Configs configs = null;
	private MessageDigest md5Digest = null;
	private File file = null;
	private JAXBContext jaxbContext = null;
	private Unmarshaller jaxbUnmarshaller = null;
	private String lastChecksum = "";

	private void init() throws JAXBException, NoSuchAlgorithmException {
		if (md5Digest == null) {
			md5Digest = MessageDigest.getInstance("MD5");
			file = new File("config.xml");
			jaxbContext = JAXBContext.newInstance(Configs.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		}
		
		try {
			String currentChecksum = getFileChecksum(md5Digest, file);
			if (lastChecksum.isEmpty() || !currentChecksum.equals(lastChecksum)) {
		        configs = (Configs) jaxbUnmarshaller.unmarshal(file);
		        log.info("(Re)load config file.");
		        printConfig();
		        TrackStarter.reInit(configs);
		        SinkStarter.reInit(configs);
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
			} catch (Exception e) {
				log.warn("Exception while loading config file {}", e);
			}
			try { Thread.sleep(15_000); } catch (Exception e) {}
		}
	}
	
	public static Configs get() {
		return configs;
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
	    for (int i=0; i< bytes.length ;i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }

	   return sb.toString();
	}
	
	private void printConfig() {
		for (Config config : configs.getConfigs()) {
			log.info(config.toString());
		}
	}
	
}
