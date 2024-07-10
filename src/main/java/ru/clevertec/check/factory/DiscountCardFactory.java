package main.java.ru.clevertec.check.factory;

import main.java.ru.clevertec.check.discountCards.*;

public class DiscountCardFactory {
    public DiscountCard getDiscountCard(CardsType type) {
        DiscountCard discountCard = null;
        switch (type) {
            case C1111:
                discountCard = new Card1();
                break;
            case C2222:
                discountCard = new Card2();
                break;
            case C3333:
                discountCard = new Card3();
                break;
            case C4444:
                discountCard = new Card4();
                break;
            default: throw new IllegalArgumentException("Wrong type" + type);
        }
        return discountCard;
    }
}
