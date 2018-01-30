package week4_min_cut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private List<Edge> edgeList;
	private Map<Integer, List<Integer>> adjacencyMap;
	
	public Graph() {
		adjacencyMap = new HashMap<Integer, List<Integer>>();
	}
	
	public void addAdjacencyList(Integer node, List<Integer> connected_vertices) {
		adjacencyMap.put(node, connected_vertices);
	}
	
	public Map<Integer, List<Integer>> getGraph() {
		return adjacencyMap;
	}
	
	public List<Integer> allNodes() {
		return new ArrayList<Integer>(adjacencyMap.keySet());
	}
	
	public List<Edge> edges() {
		return edgeList;
	}
}
