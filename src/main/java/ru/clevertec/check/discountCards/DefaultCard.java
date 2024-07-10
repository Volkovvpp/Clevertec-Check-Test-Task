package main.java.ru.clevertec.check.discountCards;

public class DefaultCard implements DiscountCard{
    public static double discountAmount = 0.02;
    private String name;

    @Override
    public void addValues(double discountAmount, String name) {

    }

    @Override
    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String getName() {
        return name;
    }
}
