package sim.model.sinks;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPSink extends AbstractSink {
	
	private static final Logger log = LoggerFactory.getLogger(TCPSink.class);

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private OutputStreamWriter writer = null;

	private int port = 0;
	
	public TCPSink(String identifier, int port) {
		super(identifier);
		this.port = port;
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
				serverSocket = new ServerSocket(port);
				log.info("Create listener for {} ({})", getIdentifier(), port);
				isReady = false;
				socket = serverSocket.accept();
				log.info("{} accepted.", getIdentifier());
				isReady = true;
				writer = new OutputStreamWriter(socket.getOutputStream());

				while (!kill) {
					if (!queue.isEmpty()) {
						String message = queue.poll();
						if (message == null) continue;
						
						writer.write(message+"\r\n");
						writer.flush();
						log.debug("Send via TCP " + getIdentifier() + " : 1");
						if (queue.size() > 100) queue.clear();
					} else {
						Thread.sleep(500);
					}
				}

			} catch (InterruptedException e) {
				// Do nothing
			} catch (SocketException e) {
				// This here is an exit for killing the current thread at being at a blocking function
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
			if (null != socket) socket.close();
			if (null != serverSocket) serverSocket.close();
		} catch (Exception e1) {
			log.warn("Exception: ", e1);
		}
	}
}
