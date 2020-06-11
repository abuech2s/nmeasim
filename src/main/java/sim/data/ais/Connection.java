package sim.data.ais;

public class Connection {
	private String node1 = "";
	private String node2 = "";
	private double weight = 100000;

	public Connection(String node1, String node2, double weight) {
		this.node1 = node1.toLowerCase().trim();
		this.node2 = node2.toLowerCase().trim();
		this.weight = weight;
	}
	
	public String getNode1() {
		return node1;
	}
	
	public String getNode2() {
		return node2;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public String getTarget(String from) {
		if (node1.equals(from)) return node2;
		else return node1;
	}
	
	@Override
	public String toString() {
		return "(" + node1 + "," + node2 + " : " + weight +  ")";
	}
}
