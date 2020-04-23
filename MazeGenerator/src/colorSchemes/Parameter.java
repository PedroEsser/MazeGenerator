package colorSchemes;

public class Parameter {

	private final double minRecommended, maxRecommended;
	private final String name;
	private double value;
	
	public Parameter(double minRecommended, double maxRecommended, String name, double initialValue) {
		this.minRecommended = minRecommended;
		this.maxRecommended = maxRecommended;
		this.name = name;
		this.value = initialValue;
	}

	public double getMinRecommended() {
		return minRecommended;
	}

	public double getMaxRecommended() {
		return maxRecommended;
	}

	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
}
