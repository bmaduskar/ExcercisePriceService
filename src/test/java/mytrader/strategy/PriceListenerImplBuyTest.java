package mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import mytrader.execution.ExecutionService;
import mytrader.model.TradeSecurity;
import mytrader.price.PriceListenerImplBuy;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class PriceListenerImplBuyTest {

  @Test
  public void testInitializeStateForBuyPriceListener() {
    ExecutionService executionService = Mockito.mock(ExecutionService.class);
    TradeSecurity ibm = new TradeSecurity("IBM",50.00,100);
    
    PriceListenerImplBuy buyPriceListener = new PriceListenerImplBuy(ibm, executionService,false);

    assertThat(buyPriceListener.getSecurity()).isEqualTo(ibm);
    assertThat(buyPriceListener.getTriggerLevel()).isEqualTo(50.00);
    assertThat(buyPriceListener.getQuantityToPurchase()).isEqualTo(100);
    assertThat(buyPriceListener.isTradeExecuted()).isFalse();
  }

  @Test
  public void testBuy_whenThresholdIsMet() {
    ExecutionService executionService = Mockito.mock(ExecutionService.class);
    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);
    TradeSecurity ibm = new TradeSecurity("IBM",50.00,100);
    PriceListenerImplBuy buyPriceListener = new PriceListenerImplBuy(ibm, executionService,
        false);
    buyPriceListener.priceUpdate(ibm,25.00);

    verify(executionService, times(1))
        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
    assertThat(acString.getValue()).as("Should be IBM ")
        .isEqualTo("IBM");
    assertThat(acDouble.getValue()).as("Should be the value less than 50.00, that is 25.00")
        .isEqualTo(25.00);
    assertThat(acInteger.getValue()).as("Should be the volume purchased").isEqualTo(100);
    assertThat(buyPriceListener.isTradeExecuted())
        .as("Should be the trade is successfully executed").isTrue();
  }

  @Test
  public void testShouldNotBuy_whenThresholdIsNotMet() {
    ExecutionService executionService = Mockito.mock(ExecutionService.class);
    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);
    TradeSecurity ibm = new TradeSecurity("IBM",50.00,100);
    PriceListenerImplBuy buyPriceListener = new PriceListenerImplBuy(ibm, executionService,
        false);
    buyPriceListener.priceUpdate(ibm, 55.00);

    verify(executionService, times(0))
        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
    assertThat(buyPriceListener.isTradeExecuted())
        .as("Should be the trade is not successfully executed").isFalse();
  }

  @Test
  public void testShouldNotBuy_whenSecurityIsDifferent() {
    ExecutionService executionService = Mockito.mock(ExecutionService.class);
    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);
    TradeSecurity apple = new TradeSecurity("APPLE",50.00,100);
    PriceListenerImplBuy buyPriceListener = new PriceListenerImplBuy(apple, executionService,
            false);
    buyPriceListener.priceUpdate(apple, 55.00);

    verify(executionService, times(0))
        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
    assertThat(buyPriceListener.isTradeExecuted())
        .as("Should be the trade is not successfully executed").isFalse();
  }

  @Test
  public void testGivenSeveralPriceUpdates_whenTradeIsAlreadyExecucted_shouldBuyOnlyOnce() {
    ExecutionService executionService = Mockito.mock(ExecutionService.class);
    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);
    TradeSecurity ibm = new TradeSecurity("IBM",50.00,100);
    PriceListenerImplBuy buyPriceListener = new PriceListenerImplBuy(ibm, executionService,
        false);
    buyPriceListener.priceUpdate(ibm, 25.00);
    buyPriceListener.priceUpdate(ibm, 10.00);
    buyPriceListener.priceUpdate(ibm, 35.00);

    verify(executionService, times(1))
        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
    assertThat(acString.getValue()).as("Should be IBM ")
        .isEqualTo("IBM");
    assertThat(acDouble.getValue()).as("Should be the value less than 50.00, that is 25.00")
        .isEqualTo(25.00);
    assertThat(acInteger.getValue()).as("Should be the volume purchased").isEqualTo(100);
    assertThat(buyPriceListener.isTradeExecuted())
        .as("Should be the trade is successfully executed").isTrue();
  }
}
