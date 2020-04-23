package guiUtils;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SliderValueChooser extends JPanel{
	
	private static final int DEFAULTRANGE = 500;
	private final double initialValue;
	private final double midValue; 
	private final double amplitude;
	protected final JTextField valueField;
	private final JSlider slider;
	private double value;
	private boolean mouseIsPressed;
	
	public SliderValueChooser(String name, double initialValue, double min, double max) {
		this.initialValue = initialValue;
		value = initialValue;
		midValue = (min + max)/2;
		amplitude = (max - min)/2;
		setLayout(new GridLayout(1,3));
		JTextField nameField = new JTextField(name + " (" + min + " - " + max + "):");
		nameField.setEditable(false);
		add(nameField);
		
		valueField = new JTextField(initialValue + "");
		valueField.addKeyListener(new MyListener());
		add(valueField);
		
		slider = new JSlider(SwingConstants.HORIZONTAL, -DEFAULTRANGE, DEFAULTRANGE, getSliderValueFor(initialValue));
		slider.addMouseListener(new MyMouseListener());
		slider.addChangeListener((e) -> {
			if(mouseIsPressed) {
				double val = getSliderValue();
				setText(val);
				setValue(getSliderValue());
			}
		});
		add(slider);
		
	}
	
	private int getSliderValueFor(double d) {
		double diff = d - midValue;
		return (int)(diff  * DEFAULTRANGE / amplitude);
	}
	
	protected double getSliderValue() {
		return slider.getValue() * amplitude / DEFAULTRANGE + midValue;
	}
	
	public double getTextValue() {
		return Double.parseDouble(valueField.getText());
	}
	
	protected void setSliderValueFor(double d) {
		int val = getSliderValueFor(d);
		slider.setValue(val);
	}
	
	public void setText(double d) {
		valueField.setText(d + "");
	}
	
	public void restoreInitialValues() {
		changeValue(initialValue);
	}
	
	public void setValue(double val) {
		value = val;
	}
	
	public double getValue() {
		return value;
	}
	
	protected void changeValue(double val) {
		setSliderValueFor(val);
		setText(val);
		setValue(val);
	}
	
	protected void validateText() {
		if(validText()) {
			valueField.setForeground(Color.BLACK);
			double val = getTextValue();
			setValue(val);
			SwingUtilities.invokeLater(() -> {setSliderValueFor(val);});
		}else
			valueField.setForeground(Color.RED);
	}
	
	protected boolean validText() {
		try {
			getTextValue();
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	private class MyListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				validateText();
		}
		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	}
	
	private class MyMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {
			mouseIsPressed = true;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			mouseIsPressed = false;
		}
	}
	
}