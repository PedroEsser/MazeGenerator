package guiUtils;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Maze.Maze;
import costumIterators.IndexedIterator;
import floodFillIterators.FillIterator;
import mazeBuilders.MazeBuilderPlus;
import pathFinders.FloodFillPlus;

public class MazeVisualizer extends JFrame{
	
	private JLabel imageHolder = new JLabel();
	private JScrollPane pane;
	private IndexedIterator it;
	
	public MazeVisualizer() {
		super();
		setLayout(new BorderLayout());
		pane = new JScrollPane(imageHolder);
		add(pane, BorderLayout.CENTER);
		JButton b = new JButton("Menu");
		b.addActionListener((e) -> {
			
		});
		add(b, BorderLayout.SOUTH);
	}
	
	public MazeVisualizer(IndexedIterator it) {
		this();
		setIterator(it);
	}
	
	public void generateMaze(int width, int height) {
		MazeBuilderPlus m = new MazeBuilderPlus(width, height);
		m.start();
		try {
			m.join();
		} catch (InterruptedException e) {
		}
		FloodFillPlus f = new FloodFillPlus(new Maze(m.getGrid()));
		f.start();
		try {
			f.join();
		} catch (InterruptedException e) {
		}
		setIterator(new FillIterator(f));
		updateUI();
	}
	
	public void setIterator(IndexedIterator it) {
		this.it = it;
		imageHolder.setIcon((Icon) it.iterator().next());
		imageHolder.updateUI();
	}
	
	public void update() {
		
	}
	
}
