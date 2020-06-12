package sim.model.sinks;

public abstract class AbstractSink implements ISink {

	private Thread thread;
	
	protected boolean kill = false;
	protected String identifier = "";
	
	protected abstract void close();
	
	protected AbstractSink(String identifier) {
		this.identifier = identifier.toLowerCase().trim();
	}
	
	public void start() {
		thread = new Thread(this);
		thread.setDaemon(false);
		thread.start();
	}
	
	public void kill() {
		thread.interrupt();
		
		//We use this trick here to kill the current thread while waiting for an accepting socket
		close();
		
		this.kill = true;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
}
