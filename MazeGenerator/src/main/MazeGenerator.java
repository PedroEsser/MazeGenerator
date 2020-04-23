package main;

import java.awt.image.BufferedImage;

import Maze.Maze;
import dataStructure.Grid;
import mazeBuilders.MazeBuilderPlus;
import pathFinders.FloodFillPlus;
import utils.MazeUtils;

public class MazeGenerator {

	private MazeBuilderPlus mb;
	private FloodFillPlus fl;
	private int scale;
	
	public MazeGenerator(BufferedImage img, int scale) {
		this.scale = scale;
		solveMaze(MazeUtils.convertToGrid(img));
	}
	
	public MazeGenerator(int width, int height, int scale) {
		this.scale = scale;
		mb = new MazeBuilderPlus(width, height);
		mb.initImage(scale);
		mb.start();
		try {
			mb.join();
		} catch (InterruptedException e) {
		}
		solveMaze(mb.getGrid());
	}
	
	private void solveMaze(Grid g) {
		fl = new FloodFillPlus(new Maze(g));
		fl.initImage(scale);
		fl.start();
		try {
			fl.join();
		} catch (InterruptedException e) {
		}
	}
	
	public void destroyWalls(double percent) {
		mb.destroyXPercentWalls(percent);
		solveMaze(mb.getGrid());
	}
	
	public FloodFillPlus getFloodFill() {
		return fl;
	}
	
	public MazeBuilderPlus getMazeBuilder() {
		return mb;
	}
	
}
