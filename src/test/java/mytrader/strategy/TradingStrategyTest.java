package mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import mytrader.execution.ExecutionService;
import mytrader.model.TradeSecurity;
import mytrader.price.PriceListener;
import mytrader.price.PriceSourceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class TradingStrategyTest {

  
  @Test
  public void testAutoBuyForSuccessfulBuy() throws InterruptedException {
    ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
    TradeSecurity security=new TradeSecurity("IBM", 50, 25);
    PriceSourceImpl priceSource = new MockPriceSource(security, 25.00);
    ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
    ArgumentCaptor<Integer> volumeCaptor = ArgumentCaptor.forClass(Integer.class);
    TradingStrategy tradingStrategy = new TradingStrategy(tradeExecutionService, priceSource);
    List<TradeSecurity> input = Arrays.asList(new TradeSecurity("IBM", 50.00, 10));
    tradingStrategy.autoBuy(input);
    verify(tradeExecutionService, times(1))
        .buy(securityCaptor.capture(), priceCaptor.capture(), volumeCaptor.capture());
    assertThat(securityCaptor.getValue()).isEqualTo("IBM");
    assertThat(priceCaptor.getValue()).isEqualTo(25.00);
    assertThat(volumeCaptor.getValue()).isEqualTo(10);
  }

  @Test
  public void testAutoBuyForNotSuccessfulBuy() throws InterruptedException {
    ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
    TradeSecurity security=new TradeSecurity("IBM", 50, 25);
    PriceSourceImpl priceSource = new MockPriceSource(security, 25.00);

    TradingStrategy tradingStrategy = new TradingStrategy(tradeExecutionService, priceSource);
    List<TradeSecurity> input = Arrays.asList(new TradeSecurity("APPL", 50.00, 10));
    tradingStrategy.autoBuy(input);
    verifyZeroInteractions(tradeExecutionService);
  }

  private class MockPriceSource extends PriceSourceImpl {

    TradeSecurity security;
    double price;

    MockPriceSource(TradeSecurity security, double price) {
      this.security = security;
      this.price = price;
    }

    private final List<PriceListener> priceListeners = new CopyOnWriteArrayList<>();

    @Override
    public void addPriceListener(PriceListener listener) {
      priceListeners.add(listener);
    }

    @Override
    public void removePriceListener(PriceListener listener) {
      priceListeners.remove(listener);
    }

    @Override
    public void tradeStock() {
      priceListeners.forEach(priceListener -> priceListener.priceUpdate(security, price));
    }
  }
}
