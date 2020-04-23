package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import colorSchemes.ColorScheme;
import colorSchemes.RainbowCS;
import guiUtils.ColorSchemeChooser;
import guiUtils.ValueChooser;
import main.MazeGenerator;

public class Menu extends JFrame{

	private final static int DEFAULTWIDTH = 600;
	private final static int DEFAULTHEIGHT = 400;
	private final JPanel panel;
	private final ValueChooser[] dimensions = new ValueChooser[3];
	
	public Menu() {
		super("MazeGenerator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
		setLocationRelativeTo(null);
		panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		dimensions[0] = new OddValueChooser("Width (should be odd)", 101);
		dimensions[1] = new OddValueChooser("Height (should be odd)", 81);
		dimensions[2] = new ValueChooser("Scale", 5);
		panel.add(dimensions[0]);
		panel.add(dimensions[1]);
		panel.add(dimensions[2]);
		JButton generateButton = new JButton("Generate Maze!");
		generateButton.addActionListener((e) -> {
			MazeGenerator mg = new MazeGenerator(dimensions[0].getValue(), dimensions[1].getValue(), dimensions[2].getValue());
			new MazeVisualizer(mg);
		});
		panel.add(generateButton);
		JButton importButton = new JButton("Import Maze");
		importButton.addActionListener((e) -> {
			new ImportWindow();
		});
		panel.add(importButton);
		add(panel);
		setVisible(true);
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
