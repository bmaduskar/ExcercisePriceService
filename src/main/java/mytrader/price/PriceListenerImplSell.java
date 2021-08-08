package mytrader.price;

import mytrader.execution.ExecutionService;
import mytrader.model.Security;
import mytrader.model.TradeSecurity;


public class PriceListenerImplSell implements PriceListener {

  private final Security security;
  private final double triggerLevel;
  private final int quantityToSell;
  private final ExecutionService executionService;

  private boolean tradeExecuted;

  public PriceListenerImplSell(TradeSecurity security,
		ExecutionService executionService, boolean tradeExecuted) {
	super();
	this.security = security;
	this.triggerLevel = security.getPriceThreshold();
	this.quantityToSell = security.getVolumeToBuy();
	this.executionService = executionService;
	this.tradeExecuted = tradeExecuted;
  }

@Override
  public void priceUpdate(Security security,double price) {
    if (canSell(price)) {
      executionService.sell(security.getSecurity(), price, quantityToSell);
      tradeExecuted = true;
    }
  }

  private boolean canSell(double price) {
    return (!tradeExecuted) && (price > this.triggerLevel);
  }
}
