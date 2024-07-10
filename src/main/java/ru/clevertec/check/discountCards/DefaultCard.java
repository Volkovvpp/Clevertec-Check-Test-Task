package main.java.ru.clevertec.check.discountCards;

public class DefaultCard implements DiscountCard{
    private static String name;

    public static void setName(String name) {
        DefaultCard.name = name;
    }

    @Override
    public void addValues(double discountAmount, String name) {

    }

    @Override
    public double getDiscountAmount() {
        return 0.02;
    }

    @Override
    public String getName() {
        return name;
    }
}
