package mazeBuilders;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

import costumIterators.IndexedIterator;
import utils.ImageUtils;
import utils.MazeUtils;

public class MazeBuilderPlus extends MazeBuilder{

	private final int WHITE = 0xffffffff;
	private final int BLUE = 0x000000ff;
	private final ArrayList<Step> steps = new ArrayList<>();
	private final HashSet<Integer> standingWalls = new HashSet<>();
	private BufferedImage img;
	int scale;

	public MazeBuilderPlus(int w, int h) {
		super(w, h);
		addAllWalls();
	}

	private final void addAllWalls() {
		for (int i = 1; i < grid.getHeight() - 1; i++)
			for (int j = i % 2 == 0 ? 1 : 2; j < grid.getWidth() - 1; j += 2)
				standingWalls.add(grid.convertToPos(j, i));
	}

	@Override
	protected void walk(int direction) {
		standingWalls.remove(current + direction);
		fillPosition(current + direction, WHITE);
		super.walk(direction);
		fillPosition(current, WHITE);
		steps.add(new Step(current, false));
	}

	@Override
	protected boolean step() {
		int nextDirection = randomPossibleDirection();
		if (nextDirection == 0) {
			current = path.pop();
			steps.add(new Step(current, true));
			return !path.isEmpty();
		}
		walk(nextDirection);
		return true;
	}

	public void destroyXPercentWalls(double percent) {
		if(percent < 0 || percent > 1)
			throw new IllegalArgumentException("Percent must be between 0 and 1! " + percent);
		int n = (int) (standingWalls.size() * percent);
		ArrayList<Integer> aux = new ArrayList<>(standingWalls);
		for (int i = 0; i < n; i++) {
			int p = aux.remove(random(aux.size()));
			standingWalls.remove(p);
			grid.setPath(p);
		}
	}

	private void fillPosition(int pos, int rgb) {
		Point aux = grid.convertToPoint(pos);
		for (int i = 0; i < scale; i++)
			for (int j = 0; j < scale; j++)
				img.setRGB(scale*aux.x + i, scale*aux.y + j, rgb);
	}

	public void setImageScale(int scale) {
		this.scale = scale;
		img = MazeUtils.convertToImage(grid);
		img = ImageUtils.getScaledImage(img, scale);
	}

	private class Step{
		int position;
		boolean backTracked;
		public Step(int p, boolean b) {
			position = p;
			backTracked = b;
		}
	}
	
	private class BuildIterator extends IndexedIterator{
		
		int currentStep = 0;

		@Override
		protected boolean validIndex(int index) {
			return index < steps.size() && index >= 0;
		}

		@Override
		public BufferedImage get(int index) {
			return null;
		}
		
	}
	
//	@Override
//	public BufferedImage getImage() {
//		return img;
//	}
//
//	@Override
//	public boolean next() {
//		return step();
//	}
//
//	@Override
//	public int getPixels() {
//		return grid.getBitArray().size();
//	}

}
