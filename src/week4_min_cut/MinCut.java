package week4_min_cut;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MinCut {

	private int minCut(Graph graph) {
		while (graph.allNodes().size() > 2) {
			Edge m = selectRandomEdge(graph);
		}
		return -1;
	}
	
	private Edge selectRandomEdge(Graph graph) {
		int random_number = new Random().nextInt(graph.edges().size());
		
		return graph.edges().remove(random_number);
	}
	
	private void contractGraph(Edge removed_edge, Graph graph) {
		//1. remove the edge from each node's list
		List<Node> nodes_to_merge = removed_edge.nodes();
		for (Node node_to_merge : nodes_to_merge) {
			node_to_merge.edges().remove(removed_edge);
			
		}
		//2. remove each node from the graph
		
		//3. create new merged node
		//4. point all non-self looping edges to the new merged node (ie add the new merged not to each edges list of nodes)
		//5. add merged node to graph
	}
	
	public static void main(String[] args) {
		//read in graph
		//map graph to map of lists. each key is the node and the value is the list of nodes to which its connected
		//cal minCut

	}

}
