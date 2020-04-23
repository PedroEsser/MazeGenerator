package costumIterators;

import java.awt.image.BufferedImage;

import pathFinders.FloodFillPlus;

public class FillIterator extends IndexedIterator {

	private int currentStep = 0;
	private StepInterface si;

	public FillIterator(StepInterface si) {
		this.si = si;
	}

	public void go(int step) {
		if (!validIndex(step))
			return;
		if (step < currentStep)
			for (; currentStep > step;)
				si.clearStep(--currentStep);
		else
			for (; currentStep < step;)
				si.fillStep(currentStep++);
	}

	@Override
	public BufferedImage get(int index) {
		go(index);
		return si.getImage();
	}

	@Override
	public boolean validIndex(int index) {
		return index >= 0 && index <= si.lastStep() + 1;
	}

	@Override
	public int size() {
		return si.lastStep();
	}

}
