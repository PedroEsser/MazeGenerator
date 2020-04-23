package guiUtils;

import costumIterators.IndexedIterator;

public class SliderIterator extends SliderValueChooser{

	private IndexedIterator it;
	
	public SliderIterator(String name, IndexedIterator it) {
		super(name, 0, 0, it.size());
		this.it = it;
	}
	
	@Override
	public void setText(double d) {
		valueField.setText((int)d + "");
	}
	
	@Override
	public void setValue(double val) {
		super.setValue(val);
		it.get((int)val);
	}
	
	@Override
	protected boolean validText() {
		return super.validText() && it.validIndex((int)getTextValue());
	}
	
	@Override
	protected void changeValue(double val) {
		if(it.validIndex((int)val))
			super.changeValue(val);
	}

}
