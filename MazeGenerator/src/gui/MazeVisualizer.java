package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Maze.Maze;
import colorSchemes.ColorScheme;
import colorSchemes.GrayScaleCS;
import colorSchemes.RainbowCS;
import costumIterators.FillIterator;
import costumIterators.IndexedIterator;
import costumIterators.StepInterface;
import dataStructure.Grid;
import guiUtils.ColorSchemeChooser;
import guiUtils.SliderValueChooser;
import guiUtils.StepIterator;
import main.MazeGenerator;
import mazeBuilders.MazeBuilderPlus;
import pathFinders.FloodFillPlus;

public class MazeVisualizer extends JFrame{
	
	private MazeGenerator mg;
	private MazeView mv;
	private JPanel panel;
	private MazeImageHolder holder;
	private MazeBuilderPlus mb;
	private FloodFillPlus f;
	private IndexedIterator it;
	private int currentStage = 0;
	private ColorScheme cs = new RainbowCS();
	SliderValueChooser test;
	boolean toggle;
	
	public MazeVisualizer(MazeGenerator mg) {
		this.mg = mg;
		//setupEverything();
		setDefaultView();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void setDefaultView() {
		FloodFillPlus fl = mg.getFloodFill();
		addMazeView(fl);
		mv.setColorScheme(cs);
		
		SliderValueChooser sld = new SliderValueChooser("Destroy % walls", 0, 0, 1);
		JButton b = new JButton("Destroy walls!");
		b.addActionListener(e -> {
			destroyWalls(sld.getValue());
		});
		mv.add(sld);
		mv.add(b);
		addLastTings();
	}
	
	public void setBuilderView() {
		addMazeView(mg.getMazeBuilder());
		addLastTings();
	}
	
	public void destroyWalls(double percent) {
		mg.destroyWalls(percent);
		setDefaultView();
	}
	
	private void addMazeView(StepInterface si) {
		if(mv != null)
			remove(mv);
		mv = new MazeView(si);
	}
	
	private void addLastTings() {
		JButton b = new JButton("Change View!");
		b.addActionListener(e -> {
			toggle = !toggle;
			if(toggle) {
				setBuilderView();
			}else {
				setDefaultView();
			}
		});
		mv.add(b);
		add(mv);
		addKeyListener(mv);
		mv.updateUI();
	}
	
	private void setupEverything() {
		panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		FloodFillPlus fl = mg.getFloodFill();
		fl.setColorScheme(cs);
		holder = new MazeImageHolder(fl.getImage());
		panel.add(holder.getPane());
		
		JPanel paintPanel = new JPanel();
		paintPanel.setLayout(new BorderLayout());
		StepIterator si = new StepIterator(new FillIterator(fl), holder);
		panel.add(si, BorderLayout.NORTH);
		panel.add(new ColorSchemeChooser(cs), BorderLayout.CENTER);
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		SliderValueChooser sld = new SliderValueChooser("Destroy % walls", 0, 0, 1);
		panel.add(sld, BorderLayout.NORTH);
		JButton b = new JButton("Destroy walls!");
		b.addActionListener(e -> {
			mg.destroyWalls(sld.getValue());
			mg.getFloodFill().setColorScheme(cs);
			holder.updateImage(mg.getFloodFill().getImage());
		});
		panel.add(b, BorderLayout.SOUTH);
		//paintPanel.add(botPanel, BorderLayout.SOUTH);
		//panel.add(paintPanel);
		
		
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void generateMaze(int width, int height, int scale) {
		mb = new MazeBuilderPlus(width, height);
		mb.start();
		try {
			mb.join();
		} catch (InterruptedException e) {
		}
		reset(mb.getGrid());
	}
	
	private void reset(Grid g) {
		/*currentStage = 0;
		f = new FloodFillPlus(new Maze(g));
		f.setColorScheme(cs);
		f.initImage(scale);
		f.start();
		try {
			f.join();
		} catch (InterruptedException e) {
		}
		setIterator(new FillIterator(f));
		test = new SliderValueChooser("Step", 0, 0, it.size()){
			@Override
			protected void changeValue() {
				int val = (int)getSliderValue();
				valueField.setText(val + "");
				it.get(val);
				holder.updateUI();
			}
		};*/
	}
	
	public void setIterator(IndexedIterator it) {
		/*this.it = it;
		if(holder == null)
			holder = new MazeImageHolder(this);
		holder.setImage(it.get(0));
		holder.updateUI();*/
	}
	
	public void setColorScheme(RainbowCS cs) {
		this.cs = cs;
		f.setColorScheme(cs);
		panel.grabFocus();
	}
	
	private class KeyHandler implements KeyListener{

		private HashMap<Character, Runnable> actions = new HashMap<>();
		
		public KeyHandler() {
			//actions.put('+', () -> currentStage = f.validStage(currentStage+1) ? currentStage+1 : currentStage);
			actions.put('-', () -> currentStage = currentStage == -1 ? -1 : currentStage-1);
			actions.put('f', () -> currentStage = it.size()-1);
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("swdefghjkl");
			char c = e.getKeyChar();
			if(actions.containsKey(c)) {
				actions.get(c).run();
				it.get(currentStage);
				//holder.updateUI();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
		
	}
	
}
