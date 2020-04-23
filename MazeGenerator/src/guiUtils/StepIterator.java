package guiUtils;

import costumIterators.IndexedIterator;
import gui.MazeImageHolder;

public class StepIterator extends SliderIterator{

	private final MazeImageHolder imageHolder;
	
	public StepIterator(IndexedIterator it, MazeImageHolder imghld) {
		super("Step", it);
		this.imageHolder = imghld;
	}
	
	public void next() {
		changeValue((int)getValue() + 1);
	}
	
	public void previous() {
		changeValue((int)getValue() - 1);
	}
	
	@Override
	public void setValue(double d) {
		super.setValue(d);
		imageHolder.updateImage();
	}

}
