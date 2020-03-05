package pathFinders;

public class Node{
	
	public final Node parent;
	public final int data;
	
	public Node(Node parent, int data) {
		this.parent = parent;
		this.data = data;
	}
	
}