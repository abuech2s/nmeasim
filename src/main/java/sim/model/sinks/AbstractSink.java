package sim.model.sinks;

public abstract class AbstractSink implements ISink {

	private Thread thread;
	
	protected boolean kill = false;
	protected String identifier = "";
	
	protected AbstractSink(String identifier) {
		this.identifier = identifier.toLowerCase().trim();
	}
	
	public void start() {
		thread = new Thread(this);
		thread.setDaemon(false);
		thread.start();
	}
	
	public void kill() {
		this.kill = true;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
}
