package sim.model.sinks;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPSink extends AbstractSink {
	
	private static final Logger log = LoggerFactory.getLogger(UDPSink.class);

	private DatagramSocket datagramSocket = null;
	
	private String ip = "";
	private int port = 0;

	public UDPSink(String identifier, String ip, int port) {
		super(identifier);
		this.port = port;
		this.ip = ip;
	}

	@Override
	public void take(String message) {
		if (isReady) queue.add(message);
		if (queue.size() > 100) queue.poll();
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
				datagramSocket = new DatagramSocket(new InetSocketAddress(this.ip, this.port));;
				log.info("Create udp sink for {} ({}:{})", getIdentifier(), ip, port);
				isReady = true;

				while (!kill) {
					if (!queue.isEmpty()) {
						String message = queue.poll();
						if (message == null) continue;
					
						byte[] bytes = message.getBytes();
						DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
						datagramSocket.send(packet);
						log.debug("Send: {}", message);
						if (queue.size() > 100) queue.clear();
					} else {
						Thread.sleep(500);
					}
				}

			} catch (InterruptedException e) {
				log.debug("Exception: ", e);
			} catch (SocketException e) {
				//This here is an exit for killing the current thread at being at a blocking function
				log.debug("Exception: ", e);
			} catch (Exception e1) {
				log.warn("Exception: ", e1);
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
