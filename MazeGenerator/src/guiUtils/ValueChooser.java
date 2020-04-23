package guiUtils;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ValueChooser extends JPanel{

	private JTextField valueField;
	
	public ValueChooser(String name, int initialValue) {
		setLayout(new GridLayout(1, 2));
		JTextField nameField = new JTextField(name + ":");
		nameField.setEditable(false);
		add(nameField);
		
		valueField = new JTextField(initialValue + "");
		valueField.getDocument().addDocumentListener(new DocListener());
		add(valueField);
	}
	
	public void setValue(int value) {
		valueField.setText(value + "");
	}
	
	public int getValue() {
		return Integer.parseInt(valueField.getText());
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
