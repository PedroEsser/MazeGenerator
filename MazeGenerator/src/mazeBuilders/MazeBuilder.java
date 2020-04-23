package mazeBuilders;

import java.util.ArrayList;
import java.util.Stack;

import dataStructure.Grid;
import utils.ImageUtils;
import utils.MazeUtils;

public class MazeBuilder extends Thread {

	protected final Grid grid;
	protected final Stack<Integer> path;
	protected int current;

	public MazeBuilder(int w, int h) {
		grid = new Grid(w , h);
		path = new Stack<>();
		startRandomPosition();
	}

	@Override
	public void run() {
		long timeStamp = System.nanoTime();
		buildMaze();
		System.out.println((System.nanoTime() - timeStamp) / 1000000d + " ms to build Maze");
	}

	protected void buildMaze() {
		System.out.println("Building Maze...");
		while (step())
			;
	}

	protected boolean step() {
		int nextDirection = randomPossibleDirection();
		if (nextDirection == 0) {
			current = path.pop();
			return !path.isEmpty();
		}
		walk(nextDirection);
		return true;
	}

	protected void walk(int direction) {
		grid.setPath(current + direction);
		grid.setPath(current + 2 * direction);
		current += 2 * direction;
		path.add(current);
	}

	private void startRandomPosition() {
		int x = (random(grid.getWidth() / 2) * 2) + 1;
		int y = (random(grid.getHeight() / 2) * 2) + 1;
		current = grid.convertToPos(x, y);
		path.add(current);
	}

	protected int random(int n) {
		return (int) (Math.random() * n);
	}

	protected int randomPossibleDirection() {
		ArrayList<Integer> possibleDirections = new ArrayList<>(4);
		for (int d : grid.getDirections())
			try {
				int aux = current + 2 * d;
				if (grid.isWall(aux) && !grid.isBorder(aux))
					possibleDirections.add(d);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		if (possibleDirections.isEmpty())
			return 0;
		return possibleDirections.get(random(possibleDirections.size()));
	}

	public Grid getGrid() {
		return grid;
	}

	public void saveGrid() {
		MazeUtils.saveGrid(grid, ImageUtils.getNextImageName("images", "GeneratedMaze"));
	}

}
