package dataStructure;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Grid {

	private final BitArray bitArray;
	private final int width;
	private final int height;
	private static final int BITSPERINT = 32;
	private final int[] directions;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		int nBits = width * height;
		directions = getDirections();
		bitArray = new BitArray(nBits);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean isWall(int x, int y) {
		return !bitArray.getBit(convertToPos(x, y));
	}

	public void setWall(int x, int y) {
		bitArray.setBit(convertToPos(x, y), false);
	}

	public void setPath(int x, int y) {
		bitArray.setBit(convertToPos(x, y), true);
	}

	public boolean isWall(int i) {
		return !bitArray.getBit(i);
	}

	public void setWall(int i) {
		bitArray.setBit(i, false);
	}

	public void setPath(int i) {
		bitArray.setBit(i, true);
	}
	
	public BitArray getBitArray() {
		return bitArray;
	}

	public Point convertToPoint(int pos) {
		return new Point(pos % width, pos / width);
	}

	public int convertToPos(int x, int y) {
		return x + y * width;
	}

	public List<Integer> getCellsAround(int position) {
		LinkedList<Integer> cells = new LinkedList<>();
		for (int dir : directions())
			if (validPosition(dir + position))
				cells.add(dir + position);
		return cells;
	}

	public List<Integer> getPathsAround(int position) {
		LinkedList<Integer> cells = new LinkedList<>();
		for (int dir : directions()) {
			int aux = dir + position;
			if (validPosition(aux) && !isWall(aux))
				cells.add(aux);
		}
		return cells;
	}
	
	public boolean isBorder(int pos) {
		Point p = convertToPoint(pos);
		return p.x == 0 || p.x == width - 1 || p.y == 0 || p.y == height - 1 || pos >= width*height;
	}

	private int[] getDirections() {
		int[] directions = new int[4];
		directions[0] = -width;
		directions[1] = 1;
		directions[2] = width;
		directions[3] = -1;
		return directions;
	}

	public boolean validPosition(int position) {
		return bitArray.validPosition(position);
	}

	public int[] directions() {
		return directions;
	}

}
