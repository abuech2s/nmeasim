package sim.model.sinks;

import java.util.List;

public interface ISink extends Runnable {

	public void start();
	public void kill();
	public String getIdentifier();
	public void take(String item);
	public void take(List<String> item);
}
