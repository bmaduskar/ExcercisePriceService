package mytrader.strategy;

import static java.util.Arrays.asList;

import java.util.List;

import mytrader.execution.ExecutionService;
import mytrader.execution.ExecutionServiceImpl;
import mytrader.model.TradeSecurity;
import mytrader.price.PriceListenerImplBuy;
import mytrader.price.PriceSource;
import mytrader.price.PriceSourceImpl;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */

public class TradingStrategy {

  private final ExecutionService equityExecutionService;
  private final PriceSource priceSource;

  
  
  public TradingStrategy(ExecutionService equityExecutionService, PriceSource priceSource) {
	super();
	this.equityExecutionService = equityExecutionService;
	this.priceSource = priceSource;
	this.priceSource.populateDB();
}

public ExecutionService getEquityExecutionService() {
	return equityExecutionService;
}

public PriceSource getPriceSource() {
	return priceSource;
}

public void autoBuy(List<TradeSecurity> request) throws InterruptedException {
	request.stream().map(r -> new PriceListenerImplBuy(r,equityExecutionService,false))
    	.forEach(priceSource::addPriceListener);
    priceSource.tradeStock();
    request.stream().map(r -> new PriceListenerImplBuy(r,equityExecutionService,false))
    .forEach(priceSource::removePriceListener); 
  }

  //This is a demo test
  public static void main(String[] args) throws InterruptedException {
	  System.out.println("Going to check for prices in program");
	TradingStrategy tradingStrategy = new TradingStrategy(new ExecutionServiceImpl(),
        new PriceSourceImpl());
    final TradeSecurity ibm = new TradeSecurity("IBM",100.00,12);
        
    final TradeSecurity google = new TradeSecurity("APPLE",120.00,18); 
    tradingStrategy.autoBuy(asList(ibm, google));
  }
}
