package sim.model.sinks;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.config.Constants;

public class UDPSink extends AbstractSink {
	
	private static final Logger log = LoggerFactory.getLogger(UDPSink.class);

	private DatagramSocket datagramSocket = null;
	private InetAddress address = null;
	
	private String ip = "";
	private int queueSize = 100;

	public UDPSink(String identifier, String ip, int port, int queueSize) {
		super(identifier, port);
		this.ip = ip;
		this.queueSize = 3 * queueSize;
	}

	@Override
	public void take(String message) {
		if (isReady) queue.add(message);
		if (queue.size() > this.queueSize) queue.poll();
	}
	
	@Override
	public void take(List<String> messages) {
		for (String msg : messages) {
			take(msg);
		}
	}

	@Override
	public void run() {
		close();
		while (!kill) {
			try {
				datagramSocket = new DatagramSocket();
				address = InetAddress.getByName(ip);
				log.info("Create udp sink for {} ({}:{})", getIdentifier(), ip, port);
				isReady = true;

				while (!kill) {
					if (!queue.isEmpty()) {
						String message = queue.poll();
						if (message == null) continue;
						message += Constants.rclf;
					
						byte[] bytes = message.getBytes();
						DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
						datagramSocket.send(packet);
						log.debug("Send via UDP " + getIdentifier() + " : 1");
						if (queue.size() > queueSize) queue.clear();
					} else {
						Thread.sleep(100);
					}
				}

			} catch (BindException e) {
				// In case of a blocking port
				log.warn("Exception: ", e);
				try { Thread.sleep(5000);} catch (InterruptedException e1) {}
			} catch (InterruptedException e) {
				// Do nothing
			} catch (SocketException e) {
				//This here is an exit for killing the current thread at being at a blocking function
				log.debug("Exception: ", e);
			} catch (Exception e) {
				log.warn("Exception: ", e);
			}
			close();
		}
	}
	
	@Override
	protected void close() {
		try {
			if (null != datagramSocket) datagramSocket.close();
		} catch (Exception e1) {
			log.warn("Exception: {}", e1);
		}
	}
}
