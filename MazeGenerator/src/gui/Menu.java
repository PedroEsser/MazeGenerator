package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import colorSchemes.ColorScheme;
import colorSchemes.RainbowCS;
import guiUtils.ColorSchemeChooser;
import guiUtils.MazeVisualizer;
import guiUtils.ValueChooser;

public class Menu extends JFrame{

	private final static int DEFAULTWIDTH = 600;
	private final static int DEFAULTHEIGHT = 400;
	private final JPanel panel;
	private ColorScheme cs = new RainbowCS();
	private final OddValueChooser[] dimensions = new OddValueChooser[2];
	
	public Menu() {
		super("MazeGenerator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
		setLocationRelativeTo(null);
		panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		dimensions[0] = new OddValueChooser("Width (has to be odd)", 601);
		dimensions[1] = new OddValueChooser("Height (has to be odd)", 601);
		panel.add(dimensions[0]);
		panel.add(dimensions[1]);
		JButton generateButton = new JButton("Generate Maze!");
		generateButton.addActionListener((e) -> {
			mv.generateMaze(dimensions[0].getValue(), dimensions[1].getValue());
		});
		panel.add(generateButton);
		JButton csc = new JButton("Change color scheme");
		csc.addActionListener((e) ->{
			new ColorSchemeChooser(this);
		});
		panel.add(csc);
		add(panel);
		setVisible(true);
	}
	
	public Dimension getDimension() {
		
	}
	
	public void setColorScheme(ColorScheme cs) {
		this.cs = cs;
	}
	
	public ColorScheme getColorScheme() {
		return cs;
	}
	
	private class OddValueChooser extends ValueChooser{

		public OddValueChooser(String name, int initialValue) {
			super(name, initialValue);
		}
		
		@Override
		protected boolean validText() {
			try {
				return getValue() % 2 == 1;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		
	}
	
}
