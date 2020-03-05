package floodFillIterators;

import java.awt.image.BufferedImage;

import costumIterators.IndexedIterator;
import pathFinders.FloodFillPlus;

public class FillIterator extends IndexedIterator{

	private int stage;
	private FloodFillPlus f;
	
	public FillIterator(FloodFillPlus f) {
		this.f = f;
	}

	@Override
	public BufferedImage get(int index) {
		if(!validIndex(index))
			return null;
		if(index < stage) 
			for(; stage > index ; stage--)
				f.eraseStage(stage);
		else
			for(; stage <= index ; stage++)
				f.fillStage(stage);
		return f.getImage();
	}

	@Override
	protected boolean validIndex(int index) {
		return f.validStage(index);
	}
	
}
