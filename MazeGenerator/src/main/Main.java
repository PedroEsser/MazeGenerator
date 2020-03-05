package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Maze.Maze;
import colorSchemes.GrayScaleCS;
import colorSchemes.RainbowCS;
import dataStructure.Grid;
import gui.GUI;
import gui.Menu;
import guiUtils.ColorSchemeChooser;
import mazeBuilders.MazeBuilder;
import mazeBuilders.MazeBuilderPlus;
import pathFinders.FloodFill;
import pathFinders.FloodFillPlus;
import utils.ImageUtils;
import utils.MazeUtils;

public class Main {

	
	public static void main(String[] args) {
		//for(int i = 0 ; i < 3 ; i++)
			//buildAndSolveMaze();
		//solveMaze("images/Test2.png", 15);
		//builderTest();
		new Menu();
		//new ColorSchemeChooser();
		
	}
	
	private static void builderTest() {
		MazeBuilderPlus m = new MazeBuilderPlus(192, 108);
		m.setImageScale(10);
		GUI gui = new GUI(m);
		gui.toggle();
		gui.start();
	}
	
	private static void buildAndSolveMaze() {
		MazeBuilderPlus m = new MazeBuilderPlus(1920/3, 1080/3);//1920/3, 1080/3		1080/3, 1920/3
		//MazeBuilder m = new MazeBuilder(100, 100);
		m.destroyXPercentWalls(.001);
		m.start();
		try {
			m.join();
		} catch (InterruptedException e) {
		}
		m.saveGrid();
		Maze maze = new Maze(m.getGrid());
		//maze.setStartMiddle();
		solveMaze(maze, 3);
	}
	
	private static void solveMaze(String path, int scale) {
		FloodFillPlus f = new FloodFillPlus(path);
		f.setImageScale(scale);
		f.start();
		try {
			f.join();
		} catch (InterruptedException e) {
		}
		//f.saveImageWithPath();
		//f.buildAndSaveImg();
		/*GUI gui = new GUI(ImageUtils.getImageFromPath("images/GeneratedMaze1.png"));
		gui.toggle();*/
		GUI gui = new GUI(f);
		gui.toggle();
		gui.start();
	}
	
	private static void solveMaze(Maze maze, int scale) {
		FloodFillPlus f = new FloodFillPlus(maze);
		//f.setColorScheme(new RainbowCS(0f, 1f, 50f));
		f.setImageScale(scale);
		f.start();
		try {
			f.join();
		} catch (InterruptedException e) {
		}
		//f.saveImageWithPath();
		//f.buildAndSaveImg();
		/*GUI gui .= new GUI(ImageUtils.getImageFromPath("images/GeneratedMaze1.png"));
		gui.toggle();*/
		GUI gui = new GUI(f);
		gui.toggle();
		gui.start();
		try{
			gui.join();
		}catch(Exception e) {
		}
		f.saveImg();
	}
	
	private static void saveRandomGrid() {
		Grid g = new Grid(80, 10);
		MazeUtils.setRandomWalls(g, 60);
		MazeUtils.saveGrid(g, "images/Grid.png");
	}
	
	private static void saveScaledGrid(Grid g, int scale) {
		BufferedImage img = MazeUtils.convertToImage(g);
		img = ImageUtils.getScaledImage(img, scale);
		ImageUtils.saveImage(img, "images/ScaledGrid" + scale +  ".png");
	}
	
}
