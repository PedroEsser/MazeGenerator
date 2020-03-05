package Maze;

import dataStructure.Grid;

public class Maze {

	private final Grid grid;
	private int start, end;
	
	public Maze(Grid grid) {
		this.grid = grid;
		setDefaultStartEnd();
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	private final void setDefaultStartEnd() {
		start = grid.convertToPos(grid.getWidth()-2, 1);		//top right corner
		end = grid.convertToPos(1, grid.getHeight()-2);			//bottom left corner
	}
	
}
