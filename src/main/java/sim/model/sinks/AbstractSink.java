package sim.model.sinks;

import java.util.concurrent.ConcurrentLinkedQueue;

import sim.config.Mode;

public abstract class AbstractSink implements ISink {

	private Thread thread;
	
	protected boolean kill = false;
	protected String identifier = "";
	
	protected ConcurrentLinkedQueue<String> queue = null;
	protected boolean isReady = false;
	protected int port = 0;
	
	protected Mode mode = Mode.PERMANENT;
	protected int amount;
	protected int counterSendMessages;
	
	protected abstract void close();
	
	protected AbstractSink(String identifier, int port, Mode mode, int amount) {
		this.identifier = identifier.toLowerCase().strip();
		queue = new ConcurrentLinkedQueue<>();
		this.port = port;
		this.amount = amount;
		this.mode = mode;
	}
	
	protected AbstractSink(String identifier, int port) {
		this.identifier = identifier.toLowerCase().strip();
		queue = new ConcurrentLinkedQueue<>();
		this.port = port;
		this.amount = 0;
		this.mode = Mode.PERMANENT;
	}
	
	public void start() {
		if (!kill) {
			thread = new Thread(this, "Sink-"+getIdentifier());
			thread.setDaemon(false);
			thread.start();
		}
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
