package guiUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import colorSchemes.ColorScheme;
import colorSchemes.RainbowCS;

public class ColorSchemeVisualizer extends JPanel{
	
	private final ColorScheme DEFAULTCS = new RainbowCS();
	private final ColorScheme cs;
	
	public ColorSchemeVisualizer() {
		cs = DEFAULTCS;
	}
	
	public ColorSchemeVisualizer(ColorScheme cs) {
		this.cs = cs;
	}
	
	public ColorScheme getCs() {
		return cs;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		final Graphics2D g2d = (Graphics2D) g;
		final AffineTransform t = g2d.getTransform();
		final double scaling = t.getScaleX();
		t.setToScale(1, 1);
		g2d.setTransform(t);
		int width = (int)Math.round(this.getWidth()*scaling);
		int height = (int)Math.round(this.getHeight()*scaling);
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		for(int i = 0 ; i < width ; i++) {
			int rgb = cs.getColorRGB((double)i/width);
			for(int j = 0 ; j < height ; j++)
				img.setRGB(i, j, rgb);
		}
		g2d.drawImage(img, 0, 0, this);
	}
	
}
