package sim.model.tracks;

import sim.model.sinks.ISink;

public interface ITrack extends Runnable {
	public void start();
	public void kill();
	public void register(ISink sink);
}
