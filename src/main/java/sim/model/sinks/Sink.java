package sim.model.sinks;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sink extends AbstractSink {
	
	private static final Logger log = LoggerFactory.getLogger(Sink.class);

	private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private OutputStreamWriter writer = null;
	private boolean isReady = false;
	private int port = 0;

	
	public Sink(String identifier, int port) {
		super(identifier);
		this.port = port;
		log.info("Sink started.");
	}

	public void take(String message) {
		if (isReady) queue.add(message);
		if (queue.size() > 100) queue.poll();
	}
	
	public void take(List<String> messages) {
		for (String msg : messages) {
			take(msg);
		}
	}

	@Override
	public void run() {
		while (!kill) {
			try {
				serverSocket = new ServerSocket(port);
				log.info("Create new listener on port: " + port);
				isReady = false;
				socket = serverSocket.accept();
				log.info("accepted.");
				isReady = true;
				writer = new OutputStreamWriter(socket.getOutputStream());

				while (!kill) {
					if (!queue.isEmpty()) {
						String message = queue.poll();
						writer.write(message+"\r\n");
						writer.flush();
						log.debug("Write:" + message);
						if (queue.size() > 100) queue.clear();
					} else {
						Thread.sleep(500);
					}
				}

			} catch (Exception e) {
				log.warn("Exception: ", e);
			}
			
			try {
				if (null != socket) socket.close();
				if (null != serverSocket) serverSocket.close();
			} catch (Exception e1) {
				log.warn("Exception: ", e1);
			}

		}
	}
}
