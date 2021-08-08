package mytrader.model;

public class StockSecurity extends Security{

	public StockSecurity(String securityName,int qty) {
		super(securityName);
		this.availableQty=qty;
	}

	private int availableQty=100;
	
	
	public boolean purchaseSecurity(int qty) {
		if((availableQty-qty)<availableQty) {
			return false;
		}
		availableQty=availableQty-qty;
		return true;
	}
	
	public boolean selloffSecurity(int qty) {
		if((availableQty+qty)>availableQty) {
			return false;
		}
		availableQty=availableQty+qty;
		return true;
	}
	
}
