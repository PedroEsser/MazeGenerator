package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import Maze.Maze;
import colorSchemes.GrayScaleCS;
import colorSchemes.RainbowCS;
import costumIterators.FillIterator;
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
		//buildAndSolveMaze();
		//ImageUtils.getImageFromPath("C:\\Users\\pedro\\Desktop\\BackgroundMaze\\FloodFillFloodFill23.png");
		//solveMaze("C:\\Users\\pedro\\Desktop\\BackgroundMaze\\FloodFill1.png", 3);
		//builderTest();
		new Menu();
		//new ColorSchemeChooser();
		//System.out.println(fib(1000000));
	}
	
	private static void builderTest() {
		MazeBuilderPlus m = new MazeBuilderPlus(192, 108);
		m.initImage(10);
//		GUI gui = new GUI(m);
//		gui.toggle();
//		gui.start();
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
		//m.saveGrid();
		Maze maze = new Maze(m.getGrid());
		//maze.setStartMiddle();
		solveMaze(maze, 3);
	}
	
	private static void solveMaze(String path, int scale) {
		FloodFillPlus f = new FloodFillPlus(path);
		f.initImage(scale);
		f.setColorScheme(new RainbowCS(.3f,1.3f,.9f));
		f.start();
		try {
			f.join();
		} catch (InterruptedException e) {
		}
		//f.saveImageWithPath();
		f.buildAndSaveImg();
		/*GUI gui = new GUI(ImageUtils.getImageFromPath("images/GeneratedMaze1.png"));
		gui.toggle();*/
		/*GUI gui = new GUI(new FillIterator(f));
		gui.toggle();
		gui.start();*/
	}
	
	private static void solveMaze(Maze maze, int scale) {
		FloodFillPlus f = new FloodFillPlus(maze);
		f.setColorScheme(new RainbowCS(.6f,1,-.5f));
		f.initImage(scale);
		f.start();
		try {
			f.join();
		} catch (InterruptedException e) {
		}
		randomParameters(f, 10);
		/*float offsetIncr = .01f;
		for(float i = 0 ; i < 1 ; i+=offsetIncr) {
			f.buildAndSaveImg();
			f.offset(offsetIncr);
		}*/
		//f.buildAndSaveImg();
		//f.saveImageWithPath();
		/*GUI gui .= new GUI(ImageUtils.getImageFromPath("images/GeneratedMaze1.png"));
		gui.toggle();*/
		//GUI gui = new GUI(new FillIterator(f));
		/*gui.toggle();
		gui.start();
		try{
			gui.join();
		}catch(Exception e) {
		}*/
	}
	
	private static void randomParameters(FloodFillPlus f, int n) {
		for(int i = 0 ; i < n ; i++) {
			f.setColorScheme(new RainbowCS(random(0, 1),random(.5, 1.5),random(.2, 1)));
			f.buildAndSaveImg();
		}
	}
	
	private static float random(double start, double end) {
		return (float)(start + Math.random() * (end - start));
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
