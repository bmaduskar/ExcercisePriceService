package mytrader.strategy;

import static java.util.Arrays.asList;

import java.util.List;

import mytrader.execution.ExecutionService;
import mytrader.execution.ExecutionServiceImpl;
import mytrader.model.Security;
import mytrader.price.PriceSourceImpl;
import mytrader.price.PriceSourceRunnable;
import mytrader.price.PriceListenerImplBuy;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */

public class TradingStrategy {

  private final ExecutionService equityExecutionService;
  private final PriceSourceRunnable priceSource;

  
  
  public TradingStrategy(ExecutionService equityExecutionService, PriceSourceRunnable priceSource) {
	super();
	this.equityExecutionService = equityExecutionService;
	this.priceSource = priceSource;
}

public ExecutionService getEquityExecutionService() {
	return equityExecutionService;
}

public PriceSourceRunnable getPriceSource() {
	return priceSource;
}

public void autoBuy(List<Security> request) throws InterruptedException {

    request.stream().map(
        r -> new PriceListenerImplBuy(r.getSecurity(), r.getPriceThreshold(), r.getVolume(),
        		equityExecutionService, false)).forEach(priceSource::addPriceListener);
    Thread thread = new Thread(priceSource);
    thread.start();
    thread.join();
    request.stream().map(
        r -> new PriceListenerImplBuy(r.getSecurity(), r.getPriceThreshold(), r.getVolume(),
        		equityExecutionService, false)).forEach(priceSource::removePriceListener);
  }

  //This is a demo test
  public static void main(String[] args) throws InterruptedException {
    TradingStrategy tradingStrategy = new TradingStrategy(new ExecutionServiceImpl(),
        new PriceSourceImpl());
    final Security ibm = new Security("IBM",100.00,12);
        
    final Security google = new Security("APPLE",120.00,18); 
    tradingStrategy.autoBuy(asList(ibm, google));
  }
}
