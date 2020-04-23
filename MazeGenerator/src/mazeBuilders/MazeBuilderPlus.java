package mazeBuilders;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

import colorSchemes.ColorScheme;
import costumIterators.IndexedIterator;
import costumIterators.StepInterface;
import utils.ImageUtils;
import utils.MazeUtils;

public class MazeBuilderPlus extends MazeBuilder implements StepInterface{

	private final int WHITE = 0xffffffff;
	private final int BLACK = 0;
	private final int BLUE = 0x000000ff;
	private final ArrayList<Step> steps = new ArrayList<>();
	private final HashSet<Integer> standingWalls = new HashSet<>();
	private BufferedImage img;
	private int scale;

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
	protected void buildMaze() {
		steps.add(new Step(current, false));
		super.buildMaze();
	}
	
	@Override
	protected void walk(int direction) {
		standingWalls.remove(current + direction);
		super.walk(direction);
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

	public void initImage(int scale) {
		this.scale = scale;
		img = new BufferedImage(grid.getWidth()*scale, grid.getHeight()*scale, BufferedImage.TYPE_3BYTE_BGR);
	}

	private class Step{
		int position;
		boolean backTracked;
		public Step(int p, boolean b) {
			position = p;
			backTracked = b;
		}
		@Override
		public String toString() {
			return position + ", backtracked -> " + backTracked;
		}
	}

	@Override
	public BufferedImage getImage() {
		return img;
	}

	@Override
	public void fillStep(int index) {
		Step current = steps.get(index);
		if(index != 0) {
			Step last = steps.get(index - 1);
			fillPosition(last.position, WHITE);
			if(!current.backTracked) {
				int betweenPos = (last.position + current.position)/2;
				fillPosition(betweenPos, WHITE);
			}
		}
		fillPosition(current.position, BLUE);
	}

	@Override
	public void clearStep(int index) {
		Step current = steps.get(index);
		if(index != lastStep()) {
			Step last = steps.get(index + 1);
			if(!last.backTracked) {
				fillPosition(last.position, BLACK);
				int betweenPos = (last.position + current.position)/2;
				fillPosition(betweenPos, BLACK);
			}else
				fillPosition(last.position, WHITE);
		}
		fillPosition(current.position, BLUE);
	}

	@Override
	public int lastStep() {
		return steps.size() - 1;
	}

	@Override
	public void setColorScheme(ColorScheme cs) {
		
	}

}
