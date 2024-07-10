package main.java.ru.clevertec.check.check_counter;

import java.math.BigDecimal;

public class ValueForWrite {
    int qty;
    String description;
    BigDecimal price;
    BigDecimal discount;
    BigDecimal total;

    @Override
    public String toString() {
        return qty + ";" + description + ';' + price + '$' + ';' + discount + '$' + ';' + total + '$';
    }
}
