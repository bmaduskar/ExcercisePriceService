package mytrader.price;

import mytrader.execution.ExecutionService;
import mytrader.model.Security;
import mytrader.model.TradeSecurity;

public class PriceListenerImplBuy implements PriceListener {

	private final TradeSecurity tradeSecurity;
	private final double triggerLevel;
	private final int quantityToPurchase;
	private final ExecutionService executionService;

	private boolean tradeExecuted;

	public PriceListenerImplBuy(TradeSecurity security, ExecutionService executionService, boolean tradeExecuted) {
		super();
		this.tradeSecurity = security;
		this.triggerLevel = security.getPriceThreshold();
		this.quantityToPurchase = security.getVolumeToBuy();
		this.executionService = executionService;
		this.tradeExecuted = tradeExecuted;
	}

	@Override
	public void priceUpdate(Security security, double price) {
		if (canBuy(security, price)) {
			executionService.buy(security.getSecurity(), price, quantityToPurchase);
			tradeExecuted = true;
		}
	}

	private boolean canBuy(Security stockSecurity, double price) {
		System.out.println(
				"listner buy is for " + tradeSecurity.getSecurity() + " @ advice to pbuy " + this.triggerLevel);
		System.out.println("going to purchase "
				+ ((!tradeExecuted) && tradeSecurity.equals(stockSecurity) && (price < this.triggerLevel)));
		return (!tradeExecuted) && tradeSecurity.equals(stockSecurity) && (price < this.triggerLevel);
	}

	public boolean isTradeExecuted() {
		return tradeExecuted;
	}

	public void setTradeExecuted(boolean tradeExecuted) {
		this.tradeExecuted = tradeExecuted;
	}

	public Security getSecurity() {
		return tradeSecurity;
	}

	public double getTriggerLevel() {
		return triggerLevel;
	}

	public int getQuantityToPurchase() {
		return quantityToPurchase;
	}

}
