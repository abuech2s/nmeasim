package sim.ais.data.seagraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.GeoOps;
import sim.data.ais.Connection;
import sim.data.ais.Edge;
import sim.data.ais.Node;
import sim.data.ais.Route;
import sim.model.Point;

public abstract class SeaGraph {

	private static Logger log = LoggerFactory.getLogger(SeaGraph.class);
	
	protected List<Point> habours = null;
	protected List<Connection> connections = null;
	
	private List<Node> nodes = null;
	private List<Edge> edges = null;
	
	private Queue<Node> closedlist = null;
	private PriorityQueue<Node> openlist = null;
	
	protected abstract void init();

	protected SeaGraph() {
		edges = new ArrayList<>();
		nodes = new ArrayList<>();
		habours = new ArrayList<>();
		connections = new ArrayList<>();
		init();
	}
	
	public Route getRandomRoute() {
		Point p1 = getRandomPoint();
		Point p2 = getRandomPoint();
		while (p1.getName().equalsIgnoreCase(p2.getName())) {
			p2 = getRandomPoint();
		}
		List<Point> listOfPoints = new ArrayList<>();
		List<Point> calcAStarPoints = calcAStar(p1, p2);
		
		listOfPoints.add(p1);
		listOfPoints.addAll(calcAStarPoints);
		listOfPoints.add(p2);
		
		listOfPoints = listOfPoints.stream().distinct().collect(Collectors.toList());
		
		Route route = new Route(listOfPoints, p1.getName(), p2.getName());

		return route;
	}
	
	protected Point get(String name) {
		for (Point p : habours) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}
	
	private Point getRandomPoint() {
		Point point = null;
		
		while (point == null) {
			Random rand = new Random();
			Object[] values = habours.toArray();
			Object o = values[rand.nextInt(values.length)];
			point = (Point)o;
			if (!point.IsAcity()) point = null;
		}
		return point;
	}
	
	private Node getNode(String name) {
		for (Node node : nodes) {
			if (node.getPoint().getName().equalsIgnoreCase(name)) return node;
		}
		log.warn("This should never been called.");
		return null;
	}
	
	private boolean containsConnection(Point p1, Point p2) {
		for (Connection connection : connections) {
			if (connection.getNode1().equalsIgnoreCase(p1.getName()) && connection.getNode2().equalsIgnoreCase(p2.getName()) ||
				connection.getNode2().equalsIgnoreCase(p1.getName()) && connection.getNode1().equalsIgnoreCase(p2.getName()) )
				return true;
		}
		return false;
	}
	
	private boolean nodeExists(Node node) {
		for (Node n : nodes) {
			if (node.getPoint().getName().equalsIgnoreCase(n.getPoint().getName())) {
				log.info("Node with name {} already exists.", node.getPoint().getName());
				return true;
			}
		}
		return false;
	}
	
	protected List<Point> calcAStar(Point source, Point goal) {
		
		for (Point point : habours) {
			Node newNode = new Node(point, 0.0);
			if (!nodeExists(newNode)) nodes.add(newNode);
		}
		
		for (Connection connection : connections) {
			edges.add(new Edge(getNode(connection.getNode1()), getNode(connection.getNode2()), connection.getWeight()));
			try {
				getNode(connection.getNode1()).addNeighbour(getNode(connection.getNode2()));
				getNode(connection.getNode2()).addNeighbour(getNode(connection.getNode1()));
			} catch (NullPointerException e) {
				log.warn("One of the harbours was not added to the harbour list: " + connection);
			}
		}
		
		Node startNode = getNode(source.getName());
		Node goalNode = getNode(goal.getName());
		
		log.debug("Nodes: " + nodes.size() + " Edges: " + edges.size() + " from " + startNode.getPoint().getName() + " to " + goalNode.getPoint().getName());
		
		//Check if two cities are connected directly
		if (source.IsAcity() && goal.IsAcity() && containsConnection(source, goal)) {
			List<Point> points = new ArrayList<>();
			points.add(source);
			points.add(goal);
			log.info("PATH (direct) : " + source.getName() + " -> " + goal.getName());
			return points;
		}
		
		closedlist = new ConcurrentLinkedQueue<>();
		openlist = new PriorityQueue<>(100, new Comparator<Node>() {
			public int compare(Node i, Node j) {
				if (i.getFValue() > j.getFValue()) {
					return 1;
				} else if (i.getFValue() < j.getFValue()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		
		startNode.setFValue(0.0);
		openlist.add(startNode);
		
		do {
			Node currentNode = openlist.poll();
			log.debug("CurrentNode: " + currentNode.getPoint().getName());
			if (currentNode.getPoint().equals(goalNode.getPoint())) {
				return getPath(goalNode);
			}
			
			closedlist.add(currentNode);
			
			expandNode(currentNode);
			
			log.debug("     openlist: " + Arrays.toString(openlist.toArray()));
			
		} while (!openlist.isEmpty());
		
		log.warn("No solution! Please check, if there is a connected sea network graph.");
		
		return null;
	}
	
	private boolean ClosedListContainsSuccessor(Node successor) {
		if (closedlist.isEmpty()) return false;
		for (Node node : closedlist) {
			if (node.getPoint().getName().equalsIgnoreCase(successor.getPoint().getName())) return true;
		}
		return false;
	}
	
	private boolean OpenListContainsSuccessor(Node successor) {
		if (openlist.isEmpty()) return false;
		for (Node node : openlist) {
			if (node.getPoint().getName().equalsIgnoreCase(successor.getPoint().getName())) return true;
		}
		return false;
	}
	
	private void expandNode(Node currentNode) {
		List<Node> successors = currentNode.getNeighbours();
		log.debug("     successors: " + Arrays.toString(successors.toArray()));
		
		for (Node successor : successors) {
			
			if (ClosedListContainsSuccessor(successor)) continue;
			
			double tentative_g = currentNode.getGValue() + getEdgeWeight(currentNode, successor);
			log.debug("     ten_g = " + currentNode.getGValue() + " + " + getEdgeWeight(currentNode, successor));
			
			if (OpenListContainsSuccessor(successor) && tentative_g >= successor.getGValue()) continue;
			
			successor.setParent(new Node(currentNode));
			successor.setGValue(tentative_g);
			
			double dist = GeoOps.getDistance(currentNode.getPoint().getLatitude(), currentNode.getPoint().getLongitude(), successor.getPoint().getLatitude(), successor.getPoint().getLongitude())/1000.0;
			double f = tentative_g + dist;
			log.debug("     dist(" + currentNode.getPoint().getName() + "," + successor.getPoint().getName()+") = " + dist);
		
			if (OpenListContainsSuccessor(successor)) {
				updateKey(successor, f);
			} else {
				successor.setFValue(f);
				openlist.add(new Node(successor));
			}
		}
	}
	
	private void updateKey(Node successor, double f) {
		for (Node node : openlist) {
			if (node.getPoint().getName().equalsIgnoreCase(successor.getPoint().getName())) {
				node.setParent(new Node(successor));
				node.setFValue(f);
			}
		}
	}
	
	private double getEdgeWeight(Node node1, Node node2) {
		for (Edge edge : edges) {
			if ( (edge.getNode1().getPoint().getName().equalsIgnoreCase(node1.getPoint().getName()) && edge.getNode2().getPoint().getName().equalsIgnoreCase(node2.getPoint().getName()) )
			|| (edge.getNode2().getPoint().getName().equalsIgnoreCase(node1.getPoint().getName())) && edge.getNode1().getPoint().getName().equalsIgnoreCase(node2.getPoint().getName()) ) {
				return edge.getWeight();
			}
		}
		log.warn("This should never be called.");
		return 0.0;
	}
	
	public static List<Point> getPath(Node target) {
		List<Point> points = new ArrayList<>();
		List<String> cities = new ArrayList<>();
		cities.add(target.getPoint().getName());
		for (Node node = target; node != null; node = node.getParent()) {
			if (node.getParent() == null) break;
			cities.add(node.getParent().getPoint().getName());
			points.add(node.getPoint());
		}
		Collections.reverse(cities);
		//Sometimes, two "helper" nodes are doubled, so we eliminate them here
		cities = cities.stream().distinct().collect(Collectors.toList());
		log.info("PATH: " + String.join(" -> ", cities));
		Collections.reverse(points);
		return points;
	}
	
}
