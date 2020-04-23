package pathFinders;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Maze.Maze;
import colorSchemes.ColorScheme;
import colorSchemes.RainbowCS;
import costumIterators.StepInterface;
import utils.ImageUtils;
import utils.MazeUtils;

public class FloodFillPlus extends FloodFill implements StepInterface{

	private static int WHITE = -1;
	private final static ColorScheme DEFAULTCS = new RainbowCS();
	private int scale = 1;
	private BufferedImage img;
	private ArrayList<List<Integer>> stages = new ArrayList<>();
	private ColorScheme cs = DEFAULTCS;
	
	public FloodFillPlus(Maze m) {
		super(m);
	}

	public FloodFillPlus(String imgName) {
		super(imgName);
	}

	@Override
	public boolean step() {
		saveCurrent();
		return super.step();
	}

	public void initImage(int scale) {
		this.scale = scale;
		img = MazeUtils.convertToImage(maze.getGrid());
		img = ImageUtils.getScaledImage(img, scale);
	}

	private void saveCurrent() {
		LinkedList<Integer> current = new LinkedList<>();
		for (Node n : currentPaths)
			current.add(n.data);
		stages.add(current);
	}

	private void fillPosition(int position, int rgb) {
		Point p = maze.getGrid().convertToPoint(position);
		for (int i = 0; i < scale; i++)
			for (int j = 0; j < scale; j++)
				img.setRGB(p.x * scale + i, p.y * scale + j, rgb);
	}

	@Override
	protected void addFinalNode(Node fn) {
		LinkedList<Integer> finalList = new LinkedList<>();
		finalList.add(fn.data);
		stages.add(finalList);
		super.addFinalNode(fn);
	}

	public void buildAndSaveImg() {
		buildImage();
		saveImg();
	}

	public void buildImage() {
		for (int i = 0; i < stages.size(); i++)
			fillStep(i);
	}

	public void saveImg() {
		ImageUtils.saveImage(img, ImageUtils.getNextImageName("C:\\Users\\pedro\\Desktop\\BackgroundMaze", "FloodFill"));//dirPath
	}

	@Override
	public void setColorScheme(ColorScheme cs) {
		this.cs = cs;
	}
	
	@Override
	public BufferedImage getImage() {
		return img;
	}

	@Override
	public void fillStep(int index) {
		int rgb = cs.getColorRGB((double)index / stages.size());
		for(int i : stages.get(index))
			fillPosition(i, rgb);
	}

	@Override
	public void clearStep(int index) {
		for (int i : stages.get(index))
			fillPosition(i, WHITE);
	}

	@Override
	public int lastStep() {
		return stages.size();
	}

}
