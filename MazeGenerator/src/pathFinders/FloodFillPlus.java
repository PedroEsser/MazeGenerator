package pathFinders;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Maze.Maze;
import colorSchemes.ColorScheme;
import colorSchemes.RainbowCS;
import utils.ImageUtils;
import utils.MazeUtils;

public class FloodFillPlus extends FloodFill{

	private static int WHITE = -1;
	private final static ColorScheme DEFAULTCS = new RainbowCS();
	private int scale;
	private BufferedImage img;
	private ArrayList<List<Integer>> stages = new ArrayList<>();
	private int stage = 0;
	private ColorScheme cs = DEFAULTCS;
	private float offset;
	
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

	public void setImageScale(int scale) {
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
	
	public boolean validStage(int stage) {
		return stage < stages.size() || stage >= 0;
	}
	
	public void eraseStage(int stage) {
		for (int i : stages.get(stage))
			fillPosition(i, WHITE);
	}
	
	public void fillStage(int stage) {
		int rgb = cs.getColorRGB((float)stage / stages.size() + offset);
		for (int i : stages.get(stage))
			fillPosition(i, rgb);
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
			fillStage(i);
	}
	
	public void offset() {
		offset += .002f;
		buildImage();
	}

	public void saveImg() {
		ImageUtils.saveImage(img, ImageUtils.getNextImageName(dirPath, imgName + "FloodFill"));
	}

	public void setColorScheme(ColorScheme cs) {
		this.cs = cs;
	}
	
	public BufferedImage getImage() {
		return img;
	}

//	@Override
//	public boolean next() {
//		return validStage(stage++);
//	}
//	
//	@Override
//	public int getPixels() {
//		return maze.getGrid().getBitArray().size();
//	}

}
