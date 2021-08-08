package mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import mytrader.model.Security;
import mytrader.model.StockSecurity;
import mytrader.price.PriceListener;
import mytrader.price.PriceSourceImpl;

public class PriceSourceImplTest {


  @Test
  public void addPriceListener_shouldAddToAList() {
    PriceListener priceListener = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener);
    assertThat(priceSource.getPriceListeners()).hasSize(1);
  }

  @Test
  public void addPriceListenerOfTwoListeners_shouldAddToAList() {
    PriceListener priceListener1 = Mockito.mock(PriceListener.class);
    PriceListener priceListener2 = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener1);
    priceSource.addPriceListener(priceListener2);

    assertThat(priceSource.getPriceListeners()).hasSize(2);
  }

  @Test
  public void removePriceListenerOfOneListeners_shouldRemoveListener() {
    PriceListener priceListener1 = Mockito.mock(PriceListener.class);
    PriceListener priceListener2 = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener1);
    priceSource.addPriceListener(priceListener2);
    priceSource.removePriceListener(priceListener2);
    assertThat(priceSource.getPriceListeners()).hasSize(1);
  }

  

  @Test
  public void givenOneListener_priceSourceShouldInvokeTheListenerWithSecurityAndPrice_whenThreadStarted() {
	  List<Security> stockDataDB=new ArrayList<>();
	  Security security=new StockSecurity("IBM", 100);
		security.setPrice(130);
		stockDataDB.add(security);
		security=new StockSecurity("APPLE", 120);
		security.setPrice(110);
		stockDataDB.add(security);
		security=new StockSecurity("GOOGLE", 80);
		security.setPrice(120);
		stockDataDB.add(security);
		security=new StockSecurity("AMAZON", 90);
		security.setPrice(140);
		stockDataDB.add(security);
		security=new StockSecurity("FACEBOOK", 150);
		security.setPrice(115);
		stockDataDB.add(security);
		
    ArgumentCaptor<Security> securityCaptor = ArgumentCaptor.forClass(Security.class);
    ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
    PriceListener priceListener = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener);
    priceSource.tradeStock();
    verify(priceListener, times(1)).priceUpdate(securityCaptor.capture(), priceCaptor.capture());
    assertThat(securityCaptor.getValue()).as("Should contain at least one value from Securities ")
        .matches(s -> stockDataDB.stream().anyMatch(stS->stS.getSecurity().equals(s.getSecurity())));
    assertThat(priceCaptor.getValue()).as("Should be a double value between 1.00 to 200.00")
        .isGreaterThan(1.00).isLessThanOrEqualTo(200.00);
  }

}
