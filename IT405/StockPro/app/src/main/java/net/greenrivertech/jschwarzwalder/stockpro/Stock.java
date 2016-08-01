package net.greenrivertech.jschwarzwalder.stockpro;

/**
 * Created by Eiseldora on 7/31/2016.
 */
public class Stock {
    private String symbol;
    private String name;
    private int quantity;
    private int price;
    private int change;
    private int value;
    private int high;
    private int low;
    private int volume;
    private int descID;

    public Stock(String symbol, String name, int price, int change, int high, int low, int volume, int descID) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.change = change;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.descID = descID;

        quantity = 0;

        value = quantity + price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        value = quantity * price;
    }

    public int getPrice() {
        return price;
    }

    public int getChange() {
        return change;
    }

    public int getValue() {
        return value;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public int getVolume() {
        return volume;
    }

    public int getDescID() {
        return descID;
    }
}
