package colorSchemes;

public class GrayScaleCS implements ColorScheme{

	private final int HELPER = 0xFF;
	private static final float DEFAULTHUEOFFSET = 0;
	private static final float DEFAULTSHIFT = 1;
	private static final float DEFAULTAMPLITUDE = 1;
	private final Parameter[] parameters;// offset, shift, amplitude
	
	public GrayScaleCS(float offset, float shift, float amplitude) {
		parameters = new Parameter[3];
		parameters[0] = new Parameter(0, 1, "Offset", offset);
		parameters[1] = new Parameter(-5, 5, "Shift", shift);
		parameters[2] = new Parameter(-10, 10, "Amplitude", amplitude);
	}
	
	public GrayScaleCS() {
		this(DEFAULTHUEOFFSET, DEFAULTSHIFT, DEFAULTAMPLITUDE);
	}
	
	@Override
	public int getColorRGB(double value) {
		value -= .5;
		if(value < 0)
			value = -value;
		double aux = Math.pow(value, parameters[1].getValue());
		aux *= parameters[2].getValue();
		aux += parameters[0].getValue();
		int val = (int)(aux * 256);
		val &= HELPER;
		int rgb = val << 8;
		rgb |= val;
		rgb <<= 8;
		rgb |= val;
		return rgb;
	}

	@Override
	public Parameter[] getParameters() {
		return parameters;
	}

}
