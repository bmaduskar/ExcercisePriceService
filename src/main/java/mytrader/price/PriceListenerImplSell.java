package mytrader.price;

import mytrader.execution.ExecutionService;


public class PriceListenerImplSell implements PriceListener {

  private final String security;
  private final double triggerLevel;
  private final int quantityToPurchase;
  private final ExecutionService executionService;

  private boolean tradeExecuted;

  public PriceListenerImplSell(String security, double triggerLevel, int quantityToPurchase,
		ExecutionService executionService, boolean tradeExecuted) {
	super();
	this.security = security;
	this.triggerLevel = triggerLevel;
	this.quantityToPurchase = quantityToPurchase;
	this.executionService = executionService;
	this.tradeExecuted = tradeExecuted;
  }

@Override
  public void priceUpdate(String security, double price) {
    if (canBuy(security, price)) {
      executionService.buy(security, price, quantityToPurchase);
      tradeExecuted = true;
    }
  }

  private boolean canBuy(String security, double price) {
    return (!tradeExecuted) && this.security.equals(security) && (price < this.triggerLevel);
  }
}
