package sim.data.ais;

import java.util.ArrayList;
import java.util.List;

import sim.model.*;

public class Node {

	private double fValue = 0.0;
	private double gValue = 0.0;
	private GeoCoordinate point = null;
	private Node parent = null;
	private List<Node> neighbours = null;
	
	public Node(Node node) {
		this.fValue = node.getFValue();
		this.gValue = node.getGValue();
		this.point = node.getPoint();
		this.parent = node.getParent();
		this.neighbours = node.getNeighbours();
	}
	
	public Node(GeoCoordinate point, double fValue) {
		this.point = point;
		this.fValue = fValue;
		neighbours = new ArrayList<>();
	}
	
	public GeoCoordinate getPoint() {
		return point;
	}
	
	public double getFValue() {
		return fValue;
	}
	
	public double getGValue() {
		return gValue;
	}
	
	public void setFValue(double fValue) {
		this.fValue = fValue;
	}
	
	public void setGValue(double gValue) {
		this.gValue = gValue;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void addNeighbour(Node neighbour) {
		neighbours.add(neighbour);
	}
	
	public List<Node> getNeighbours() {
		return neighbours;
	}
	
	@Override
	public String toString() {
		return point.getName() + "("  + (int)fValue + ")";
	}
}
