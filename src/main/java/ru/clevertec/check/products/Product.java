package main.java.ru.clevertec.check.products;

public abstract class Product {
    private double price;
    private int quantity;
    private boolean wholesale;
    private int countOfBuyingItems = 0;
    private String description;

    public void addValues(double price, int quantity, boolean wholesale, String description) {
        this.price = price;
        this.quantity = quantity;
        this.wholesale = wholesale;
        this.description = description;
    }

    public void updateCountOfBuyingItems(int count) {
        countOfBuyingItems += count;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isWholesale() {
        return wholesale;
    }

    public int getCountOfBuyingItems() {
        return countOfBuyingItems;
    }

    public String getDescription() {
        return description;
    }
}
