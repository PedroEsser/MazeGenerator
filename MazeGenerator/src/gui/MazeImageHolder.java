package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MazeImageHolder{

	private final JLabel holder;
	private JScrollPane scrollPane;
	
	public MazeImageHolder(BufferedImage img) {
		super();
		holder = new JLabel();
		holder.setIcon(new ImageIcon(img));
		holder.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX()/5 + ", " + e.getY()/5);
				holder.grabFocus();
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		JPanel panel = new JPanel();
		panel.add(holder);
		scrollPane = new JScrollPane(panel);
	}
	
	public void addKeyListener(KeyListener kl) {
		holder.addKeyListener(kl);
	}
	
	public JScrollPane getPane() {
		return scrollPane;
	}
	
	public void updateImage() {
		holder.updateUI();
	}
	
	public void updateImage(BufferedImage img) {
		holder.setIcon(new ImageIcon(img));
	}
	
}
