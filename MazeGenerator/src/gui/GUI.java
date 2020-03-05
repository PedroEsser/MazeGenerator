package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pathFinders.FloodFillPlus;

public class GUI extends Thread {

	private final JFrame frame = new JFrame();
	private final JPanel panel;
	private final BufferedImage img;
	private final long pseudoTime;
	private static final long TIMECONSTANT = 2000000000000l;

	public GUI(BufferedImage img) {
		this.img = img;
		this.pseudoTime = TIMECONSTANT / (img.getHeight()*img.getWidth());
		frame.setUndecorated(true);
		frame.setSize(img.getWidth(), img.getHeight());// * 4 / 5
		frame.setLocationRelativeTo(null);
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				final Graphics2D g2d = (Graphics2D) g;
				final AffineTransform t = g2d.getTransform();
				t.setToScale(1, 1);
				g2d.setTransform(t);
				g2d.drawImage(img, 0, 0, this);
				// g.drawImage(image, 0, 0, this);
			}
		};
		frame.add(panel);
	}

	public void toggle() {
		frame.setVisible(!frame.isVisible());
	}

	public void close() {
		frame.dispose();
	}

	@Override
	public void run() {
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
