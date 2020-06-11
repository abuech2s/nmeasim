package sim.model.tracks;

public abstract class Track implements ITrack {

	protected boolean kill = false;
	protected Thread thread;
	
	public Track() {
		
	}
	
	public void kill() {
		this.kill = true;
	}
	
	@Override
	public void start() {
		thread = new Thread(this);
		thread.setDaemon(false);
		thread.start();
	}
}
