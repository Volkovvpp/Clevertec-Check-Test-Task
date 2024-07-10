package main.java.ru.clevertec.check.discountCards;

public class Card1 implements DiscountCard{
    private static double discountAmount;
    private String name;

    @Override
    public void addValues(double discountAmount, String name) {
        Card1.discountAmount = discountAmount / 100;
        this.name = name;
    }

    @Override
    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getName() {
        return name;
    }
}
