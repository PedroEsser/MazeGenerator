package pathFinders;

import java.util.HashSet;
import java.util.LinkedList;

import Maze.Maze;

public class FloodFill extends PathFinder{  

	private static final String DIRECTORY = "FloodFills";
	private static final String IMAGENAME = "FloodFill";
	private final HashSet<Integer> positionsVisited;
	protected LinkedList<Node> currentPaths = new LinkedList<>();
	
	public FloodFill(Maze m) {
		super(m, DIRECTORY, IMAGENAME);
		positionsVisited = new HashSet<>(m.getGrid().getBitArray().size()/2);
		currentPaths.add(new Node(null, maze.getStart()));
	}
	
	public FloodFill(String imgName) {
		super(DIRECTORY, imgName);
		positionsVisited = new HashSet<>(maze.getGrid().getBitArray().size()/2);
		currentPaths.add(new Node(null, maze.getStart()));
	}

	private void buildPath(Node n) {
		path = new Path(maze.getGrid());
		while(n!=null) {
			path.put(n.data);
			n = n.parent;
		}
	}
	
	@Override
	public boolean step() {
		LinkedList<Node> nextNodes = new LinkedList<>();
		for(Node n : currentPaths)
			for(int i : maze.getGrid().getPathsAround(n.data)) {
				if(i == maze.getEnd()) {
					addFinalNode(new Node(n, i));
					//return true;
				}
				if(!positionsVisited.contains(i)){
					nextNodes.add(new Node(n, i));
					positionsVisited.add(i);
				}
			}
		currentPaths = nextNodes;
		return currentPaths.isEmpty();
	}
	
	protected void addFinalNode(Node fn) {
		buildPath(fn);
	}
	
}
