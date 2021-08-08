package mytrader.price;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import mytrader.model.Security;
import mytrader.model.StockSecurity;

public interface PriceSource {
	
	Set<Security> stockDataDB=new HashSet<>();
	
	default void populateDB() {
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
	}
	
	default void recycleDB() {
		stockDataDB.forEach(security->{
			  double leftLimit  = 1.00;
			  double rightLimit  = security.getPrice();
			  security.setPrice(leftLimit  + (rightLimit  - leftLimit) * new Random().nextDouble());
		});
	}
	
	

    void addPriceListener(PriceListener listener);

    void removePriceListener(PriceListener listener);
    
    void tradeStock();

}
