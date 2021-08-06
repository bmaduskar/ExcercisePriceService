package mytrader.price;

public interface PriceListener {

    void priceUpdate(String security, double price);

}
