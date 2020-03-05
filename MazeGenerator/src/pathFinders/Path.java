package pathFinders;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Stack;

import dataStructure.Grid;
import utils.MazeUtils;

public class Path {

	private final Stack<Integer> path;
	private final Grid grid;
	
	public Path(Grid grid) {
		path = new Stack<>();
		this.grid = grid;
	}
	
	public int get() {
		return path.peek();
	}
	
	public void put(int i) {
		path.push(i);
	}
	
	@Override
	public String toString() {
		return path + "";
	}
	
	public BufferedImage getImageWithPath() {
		BufferedImage img = MazeUtils.convertToImage(grid);
		int i = 0;
		int size = path.size();
		for(int pos : path) {
			Point p = grid.convertToPoint(pos);
			int rgb = Color.HSBtoRGB((float)i/size, 1f, 1f);
			img.setRGB(p.x, p.y, rgb);
			i++;
		}
		return img;
	}
	
}
