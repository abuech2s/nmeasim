package sim.model.sinks;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractSink implements ISink {

	private Thread thread;
	
	protected boolean kill = false;
	protected String identifier = "";
	
	protected ConcurrentLinkedQueue<String> queue = null;
	protected boolean isReady = false;
	protected int port = 0;
	
	protected abstract void close();
	
	protected AbstractSink(String identifier, int port) {
		this.identifier = identifier.toLowerCase().trim();
		queue = new ConcurrentLinkedQueue<>();
		this.port = port;
	}
	
	public void start() {
		thread = new Thread(this, "Sink-"+getIdentifier());
		thread.setDaemon(false);
		thread.start();
	}
	
	public void kill() {
		thread.interrupt();
		this.kill = true;
		
		//We use this trick here to kill the current thread while waiting for an accepting socket
		close();
	}
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
}
