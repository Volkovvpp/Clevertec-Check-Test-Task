package main.java.ru.clevertec.check.check_counter;

import main.java.ru.clevertec.check.discountCards.DiscountCard;
import main.java.ru.clevertec.check.exeptions.NotEnoughMoneyException;
import main.java.ru.clevertec.check.exeptions.BadRequestException;
import main.java.ru.clevertec.check.input.ValuesReturner;
import main.java.ru.clevertec.check.products.Product;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckCounter {
    String inputString;
    List<ValueForWrite> valueForWrites = new ArrayList<>();
    BigDecimal currentAccount = BigDecimal.valueOf(0);
    DiscountCard discountCard;
    BigDecimal currentDiscountSum = BigDecimal.valueOf(0);

    public CheckCounter(String inputString) {
        this.inputString = inputString;
    }

    public void countCheck() throws BadRequestException, NotEnoughMoneyException {

        ValuesReturner valuesReturner = new ValuesReturner(inputString);
        List<Product> productList = valuesReturner.getProductList();
        discountCard = valuesReturner.getDiscountCard();
        int balance = valuesReturner.getDebitBalance();

        for (Product product : productList) {
            ValueForWrite valueForWrite = new ValueForWrite();
            if (product.getCountOfBuyingItems() > product.getQuantity()) {
                throw new BadRequestException();
            }
            int qty = product.getCountOfBuyingItems();
            valueForWrite.qty = qty;
            valueForWrite.description = product.getDescription();
            BigDecimal saleCoefficient = BigDecimal.valueOf(0);

            if (product.isWholesale() && product.getCountOfBuyingItems() >= 5) {
                saleCoefficient = BigDecimal.valueOf(0.1);
            } else if (discountCard != null) {
                saleCoefficient = BigDecimal.valueOf(discountCard.getDiscountAmount());
            }
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            valueForWrite.price = price;
            BigDecimal total = price.multiply(BigDecimal.valueOf(qty));
            valueForWrite.total = total;
            BigDecimal discountSum = total.multiply(saleCoefficient);
            valueForWrite.discount = discountSum;

            currentDiscountSum = currentDiscountSum.add(discountSum);
            currentAccount = currentAccount.add(total);

            if (currentAccount.compareTo(BigDecimal.valueOf(balance)) > 0) throw new NotEnoughMoneyException();

            valueForWrites.add(valueForWrite);
        }
    }

    public List<ValueForWrite> getValueForWrites() {
        return valueForWrites;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public BigDecimal getCurrentAccount() {
        return currentAccount;
    }

    public BigDecimal getCurrentDiscountSum() {
        return currentDiscountSum;
    }
}