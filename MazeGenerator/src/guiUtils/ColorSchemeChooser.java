package guiUtils;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import colorSchemes.ColorScheme;
import colorSchemes.RainbowCS;
import gui.Menu;

public class ColorSchemeChooser extends JFrame{

	private static final int DEFAULTWIDTH = 600;
	private static final int DEFAULTHEIGHT = 200;
	private MyValueChooser[] values = new MyValueChooser[3];
	private ColorSchemeVisualizer csv;
	
	public ColorSchemeChooser(Menu menu) {
		super("Color scheme Visualizer");
		RainbowCS cs = (RainbowCS)menu.getColorScheme();
		this.csv = new ColorSchemeVisualizer(cs);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		
		values[0] = new MyValueChooser("Offset", cs.getOffset(), 0, 1);
		values[1] = new MyValueChooser("Shift", cs.getShift(), 0, 5);
		values[2] = new MyValueChooser("Amplitude", cs.getAmplitude(), -5, 5);
		for(MyValueChooser v : values)
			panel.add(v);
		
		panel.add(csv);
		JButton applyButton = new JButton("Apply!");
		applyButton.addActionListener((e) -> {
			menu.setColorScheme(getColorScheme());
			this.dispose();
		});
		JButton restoreButton = new JButton("Restore Values");
		restoreButton.addActionListener((e) -> {
			for(MyValueChooser v : values)
				v.restoreInitialValues();
		});
		JPanel lastPanel = new JPanel();
		lastPanel.setLayout(new GridLayout(1, 2));
		lastPanel.add(restoreButton);
		lastPanel.add(applyButton);
		
		panel.add(lastPanel);
		add(panel);
		setVisible(true);
		SwingUtilities.invokeLater(() -> {
			updateCS();
		});
	}
	
	private ColorScheme getColorScheme() {
		return new RainbowCS(values[0].getValue(), values[1].getValue(), values[2].getValue());
	}
	
	private void updateCS() {
		csv.setCS(getColorScheme());
		csv.updateUI();
	}
	
	private class MyValueChooser extends SliderValueChooser{
		
		public MyValueChooser(String name, float initialValue, float min, float max) {
			super(name, initialValue, min, max);
		}

		@Override
		protected void changeValue() {
			super.changeValue();
			updateCS();
		}
		
		@Override
		protected void validateText() {
			super.validateText();
			try {
				updateCS();
			}catch(NumberFormatException e) {}
		}
		
	}
	
}
