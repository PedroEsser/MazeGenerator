package Maze;

import java.util.ArrayList;
import java.util.List;

import dataStructure.Grid;

public class Cell {

	private final int position;
	private final Grid grid;
	
	public Cell(int position, Grid grid) {
		this.position = position;
		this.grid = grid;
	}

	public List<Integer> getCellsAround() {
		ArrayList<Integer> cells = new ArrayList<>(4);
		for(int dir : grid.directions())
			if(grid.validPosition(dir + position))
				cells.add(dir + position);
		return cells;
	}
	
}
