package colorSchemes;

import java.awt.Color;

public class RainbowCS implements ColorScheme{

	private static final float DEFAULTHUEOFFSET = 0;
	private static final float DEFAULTSHIFT = 1;
	private static final float DEFAULTAMPLITUDE = 1;
	private final Parameter[] parameters;// hueOffset, shift, amplitude
	
	public RainbowCS(float hueOffset, float shift, float amplitude) {
		parameters = new Parameter[3];
		parameters[0] = new Parameter(0, 1, "Initial hue", hueOffset);
		parameters[1] = new Parameter(0, 10, "Shift", shift);
		parameters[2] = new Parameter(-10, 10, "Amplitude", amplitude);
	}
	
	public RainbowCS() {
		this(DEFAULTHUEOFFSET, DEFAULTSHIFT, DEFAULTAMPLITUDE);
	}

	@Override
	public int getColorRGB(double value) {
		double aux = Math.pow(value, parameters[1].getValue());
		return Color.HSBtoRGB((float)(parameters[0].getValue() + parameters[2].getValue() * aux), 1f, 1f);
	}

	@Override
	public Parameter[] getParameters() {
		return parameters;
	}
	
	

}
