package gui;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import guiUtils.ValueChooser;
import main.MazeGenerator;
import utils.ImageUtils;
import utils.MazeUtils;

public class ImportWindow extends JFrame{

	private final static int DEFAULTWIDTH = 300;
	private final static int DEFAULTHEIGHT = 200;
	private final ValueChooser scaleVC;
	private BufferedImage maze;
	
	public ImportWindow() {
		super("Import a maze");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		scaleVC = new ValueChooser("Scale", 0);
		panel.add(scaleVC);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		JTextField tf = new JTextField();
		bottomPanel.add(tf);
		JButton mazeButton = new JButton("Choose Maze");
		mazeButton.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG file", "png");
	        chooser.setFileFilter(filter);
	        int returnVal = chooser.showOpenDialog(panel);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	           File mazeFile =  chooser.getSelectedFile();
	           tf.setText(mazeFile.getName());
	           maze = ImageUtils.getImageFromPath(mazeFile.getAbsolutePath());
	           scaleVC.setValue(MazeUtils.detectScale(maze));
	        }
		});
		bottomPanel.add(mazeButton);
		panel.add(bottomPanel);
		JButton goButton = new JButton("Go!");
		goButton.addActionListener((e) -> {
			MazeGenerator mg = new MazeGenerator(maze, scaleVC.getValue());
			new MazeVisualizer(mg);
		});
		panel.add(goButton);
		add(panel);
		setVisible(true);
	}
	
}
