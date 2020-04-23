package guiUtils;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import colorSchemes.ColorScheme;
import colorSchemes.Parameter;
import colorSchemes.RainbowCS;
import gui.MazeVisualizer;
import gui.Menu;

public class ColorSchemeChooser extends JPanel{

	private final MyValueChooser[] values;
	private final ColorSchemeVisualizer csv;
	
	public ColorSchemeChooser(ColorScheme cs) {
		super();
		this.csv = new ColorSchemeVisualizer(cs);
		Parameter[] pars = cs.getParameters();
		setLayout(new GridLayout(pars.length + 1,1));
		
		values = new MyValueChooser[pars.length];
		for(int i = 0 ; i < pars.length ; i++) {
			values[i] = new MyValueChooser(pars[i]);
			add(values[i]);
		}
		
		add(csv);
	}
	
	private void updateCS() {
		csv.updateUI();
	}
	
	private class MyValueChooser extends SliderValueChooser{
		
		private final Parameter par;
		
		public MyValueChooser(Parameter p) {
			super(p.getName(), p.getValue(), p.getMinRecommended(), p.getMaxRecommended());
			par = p;
		}
		
		@Override
		public void setValue(double val) {
			super.setValue(val);
			par.setValue(val);
			updateCS();
		}
		
		@Override
		protected void validateText() {
			super.validateText();
			try {
				par.setValue(getTextValue());
				updateCS();
			}catch(NumberFormatException e) {}
		}
		
	}
	
}
