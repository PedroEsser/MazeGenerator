package colorSchemes;

public class GrayScaleCS implements ColorScheme{

	@Override
	public int getColorRGB(float value) {
		int aux = (int)(value%1 * 256);
		int rgb = aux << 8;
		rgb += aux;
		rgb <<= 8;
		rgb += aux;
		return rgb;
	}

}
