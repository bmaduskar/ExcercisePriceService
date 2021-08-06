package mytrader.model;

public class Security {
	
	  private final String securityName;
	  private final double priceThreshold;
	  private final int volume;
	  
	  public Security(String securityName, double priceThreshold, int volume) {
		super();
		this.securityName = securityName;
		this.priceThreshold = priceThreshold;
		this.volume = volume;
	  }

	public String getSecurity() {
		return securityName;
	}

	public double getPriceThreshold() {
		return priceThreshold;
	}

	public int getVolume() {
		return volume;
	}

}
