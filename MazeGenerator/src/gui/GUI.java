package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import costumIterators.IndexedIterator;
import pathFinders.FloodFillPlus;

public class GUI extends JPanel {

	private final BufferedImage img;
	private final long pseudoTime;
	private static final long TIMECONSTANT = 1000000000000l;
	private IndexedIterator it;

	public GUI(IndexedIterator it) {
		this.it = it;
		this.img = it.get(0);
		this.pseudoTime = TIMECONSTANT / (img.getHeight()*img.getWidth());
//		frame.setUndecorated(true);
//		frame.setSize(img.getWidth() * 4 / 5, img.getHeight() * 4 / 5);// * 4 / 5
//		frame.setLocationRelativeTo(null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		final AffineTransform t = g2d.getTransform();
		t.setToScale(1, 1);
		g2d.setTransform(t);
		g2d.drawImage(img, 0, 0, this);
	}

	public void run() {
		for(BufferedImage img : it) {
			updateUI();
			costumSleep();
		}
		/*while (plus.next()) {
			panel.updateUI();
			//defaultSleep();
			costumSleep();
		}
		FloodFillPlus f = (FloodFillPlus) plus;
		while (true) {
			f.offset();
			panel.updateUI();
			costumSleep();
		}*/
	}

	private void costumSleep() {
		long timeStamp = System.nanoTime();
		while (System.nanoTime() - timeStamp < pseudoTime);
	}

	private void defaultSleep() {
		try {
			Thread.sleep(10, 10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sleepNanos(long nanos) {
		try {
			TimeUnit.NANOSECONDS.sleep(nanos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
