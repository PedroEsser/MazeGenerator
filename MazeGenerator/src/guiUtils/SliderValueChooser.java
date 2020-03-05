package guiUtils;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SliderValueChooser extends JPanel{
	
	private static final int DEFAULTRANGE = 500;
	private final float initialValue;
	private final float midValue;
	private final float amplitude;
	private JTextField valueField;
	private JSlider slider;
	
	public SliderValueChooser(String name, float initialValue, float min, float max) {
		this.initialValue = initialValue;
		midValue = (min + max)/2;
		amplitude = (max - min)/2;
		setLayout(new GridLayout(1,3));
		JTextField nameField = new JTextField(name + " (" + min + " - " + max + "):");
		nameField.setEditable(false);
		add(nameField);
		
		valueField = new JTextField(initialValue + "");
		valueField.getDocument().addDocumentListener(new DocListener());
		add(valueField);
		
		slider = new JSlider(SwingConstants.HORIZONTAL, -DEFAULTRANGE, DEFAULTRANGE, getSliderValueFor(initialValue));
		slider.addChangeListener((e) -> {
			changeValue();
		});
		add(slider);
	}
	
	private int getSliderValueFor(float f) {
		float diff = f - midValue;
		return (int)(diff  * DEFAULTRANGE / amplitude);
	}
	
	private float getSliderValue() {
		return slider.getValue() * amplitude / DEFAULTRANGE + midValue;
	}
	
	protected void changeValue() {
		valueField.setText(getSliderValue() + "");
	}
	
	public void restoreInitialValues() {
		slider.setValue(getSliderValueFor(initialValue));
		changeValue();
	}
	
	public float getValue() {
		return Float.parseFloat(valueField.getText());
	}
	
	protected boolean validText() {
		try {
			getValue();
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	protected void validateText() {
		if(validText())
			valueField.setForeground(Color.BLACK);
		else
			valueField.setForeground(Color.RED);
	}
	
	private class DocListener implements DocumentListener{
		@Override
		public void insertUpdate(DocumentEvent e) {
			validateText();
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			validateText();
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			validateText();
		}
	}
	
}