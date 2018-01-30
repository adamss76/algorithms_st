package week4_min_cut;

import java.util.ArrayList;
import java.util.List;

public class Edge {
	private List<Node> nodeList;
	
	public Edge(Node one_vertex, Node another_vertex) {
		nodeList = new ArrayList<Node>();
		nodeList.add(one_vertex);
		nodeList.add(another_vertex);
	}
	
	public List<Node> nodes() {
		return nodeList;
	}
}
