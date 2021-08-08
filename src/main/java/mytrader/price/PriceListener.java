package mytrader.price;

import mytrader.model.Security;

public interface PriceListener {

    void priceUpdate(Security security,double price);

}
