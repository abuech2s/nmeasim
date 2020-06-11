package sim.data.ais;

public class Edge {
	
	private Node node1;
	private Node node2;
	private double weight = 100000;

	public Edge(Node node1, Node node2, double weight) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}
	
	public Node getNode1() {
		return node1;
	}
	
	public Node getNode2() {
		return node2;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public Node getTarget(Node from) {
		if (node1.getPoint().equals(from.getPoint())) return node2;
		else return node1;
	}
	
	@Override
	public String toString() {
		return node1 + " - " + node2 + " : " + weight;
	}
	
}
