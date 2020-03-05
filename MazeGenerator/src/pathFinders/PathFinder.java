package pathFinders;

import Maze.Maze;
import utils.ImageUtils;
import utils.MazeUtils;

public abstract class PathFinder extends Thread {

	private static final String SUBPATH = "images/";
	protected Path path;
	protected final Maze maze;
	protected final String dirPath, imgName;

	public PathFinder(Maze m, String dirPath, String imgName) {
		maze = m;
		this.dirPath = SUBPATH + dirPath;
		this.imgName = imgName;
	}

	public PathFinder(String dirPath, String imgName) {
		this(new Maze(MazeUtils.convertToGrid(ImageUtils.getImageFromPath(imgName))), dirPath, imgName);
	}

	public void saveImageWithPath() {
		ImageUtils.saveImage(path.getImageWithPath(), ImageUtils.getNextImageName(dirPath, imgName + "Solved"));
	}
	
	public Path getPath() {
		return path;
	}

	public abstract boolean step();

	@Override
	public void run() {
		long l = System.nanoTime();
		System.out.println("Solving Maze...");
		while (!step());
		System.out.println((System.nanoTime() - l) / 1000000d + " ms to solve Maze");
	}

}
