package colorSchemes;

import java.awt.Color;

public class RainbowCS implements ColorScheme{

	private static final float DEFAULTOFFSET = 0;
	private static final float DEFAULTSHIFT = 1;
	private static final float DEFAULTAMPLITUDE = 1;
	private final float offset, shift, amplitude;
	
	public RainbowCS(float offset, float shift, float amplitude) {
		super();
		this.offset = offset;
		this.shift = shift;
		this.amplitude = amplitude;
	}
	
	public RainbowCS() {
		this(DEFAULTOFFSET, DEFAULTSHIFT, DEFAULTAMPLITUDE);
	}

	@Override
	public int getColorRGB(float value) {
		float aux = (float) Math.pow(value, shift);
		return Color.HSBtoRGB(offset - amplitude * aux, 1f, 1f);
	}

	public float getOffset() {
		return offset;
	}

	public float getShift() {
		return shift;
	}

	public float getAmplitude() {
		return amplitude;
	}
	
	

}
