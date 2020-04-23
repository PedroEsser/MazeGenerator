package costumIterators;

import java.awt.image.BufferedImage;

import colorSchemes.ColorScheme;

public interface StepInterface {

	public BufferedImage getImage();
	
	public void fillStep(int index);
	
	public void clearStep(int index);
	
	public int lastStep();
	
	public void setColorScheme(ColorScheme cs);
	
}
