package main.java.ru.clevertec.check.discountCards;

public interface DiscountCard {
    public void addValues(double discountAmount, String name);
    public double getDiscountAmount();
    public String getName();
}
