package week4_min_cut;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int value;
	private List<Edge> edgeList;
	
	public Node(int value) {
		this.value = value;
		edgeList = new ArrayList<Edge>();
	}
	
	public int value() {
		return value;
	}
	
	public List<Edge> edges() {
		return edgeList;
	}
}
