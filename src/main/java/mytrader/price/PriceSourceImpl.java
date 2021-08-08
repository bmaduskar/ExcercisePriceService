package mytrader.price;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import mytrader.model.Security;

public class PriceSourceImpl implements PriceSource {

	private final List<PriceListener> priceListeners = new CopyOnWriteArrayList<>();

	@Override
	public void addPriceListener(PriceListener listener) {
		this.priceListeners.add(listener);
	}

	@Override
	public void removePriceListener(PriceListener listener) {
		this.priceListeners.remove(listener);
	}

	public List<PriceListener> getPriceListeners() {
		return priceListeners;
	}

	public void tradeStock() {
		recycleDB();
		for (int i = 0; i < 5; i++) {
			Security stockSecurity = stockDataDB.toArray(new Security[stockDataDB.size()])[new Random()
					.nextInt(stockDataDB.size())];
			priceListeners.forEach(priceListener -> {
				priceListener.priceUpdate(stockSecurity, stockSecurity.getPrice());
			});
		}
	}
}
