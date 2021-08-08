package mytrader.model;

public class TradeSecurity extends Security {
	
	private final double priceThreshold;
	private final int volumeToBuy;
	
	public TradeSecurity(String securityName, double priceThreshold, int quantity) {
		super(securityName);
		this.volumeToBuy = quantity;
		this.priceThreshold=priceThreshold;
	}

	public double getPriceThreshold() {
		return priceThreshold;
	}

	public int getVolumeToBuy() {
		return volumeToBuy;
	}
	
}
