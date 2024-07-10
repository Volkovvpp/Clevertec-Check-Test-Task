package main.java.ru.clevertec.check.input;

import main.java.ru.clevertec.check.discountCards.DefaultCard;
import main.java.ru.clevertec.check.discountCards.DiscountCard;
import main.java.ru.clevertec.check.exeptions.BadRequestException;
import main.java.ru.clevertec.check.products.Product;
import main.java.ru.clevertec.check.reader_writer.DiscountCardReader;
import main.java.ru.clevertec.check.reader_writer.ProductReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValuesReturner {
    private final InputProductsForCheck inputProductsForCheck;
    private List<Product> productList = new ArrayList<>();
    private DiscountCard discountCard;
    private int debitBalance;

    public ValuesReturner(String inputString) throws BadRequestException {
        inputProductsForCheck = new InputProductsForCheck(inputString);
        countReturnValues();
    }

    public void countReturnValues() throws BadRequestException {
        ProductReader productReader = new ProductReader(inputProductsForCheck.getPathToFile());
        DiscountCardReader discountCardReader = new DiscountCardReader();

        Map<Integer, Product> productMap = productReader.parseFile();
        Map<Integer, DiscountCard> discountCardMap = discountCardReader.parseFile();

        Map<Integer, Integer> integerIntegerMap = inputProductsForCheck.getMapProduct();

        for (Map.Entry<Integer, Integer> entry : integerIntegerMap.entrySet()) {
            Integer id = entry.getKey();
            Integer value = entry.getValue();
            if (!productMap.containsKey(id)) throw new BadRequestException();
            else {
                Product productTmp = productMap.get(id);
                productTmp.updateCountOfBuyingItems(value);
                productList.add(productTmp);
            }
        }

        int discountNum = inputProductsForCheck.getDiscountCardNum();

        if (discountCardMap.containsKey(discountNum)) discountCard = discountCardMap.get(discountNum);
        else {
            discountCard = new DefaultCard();
            DefaultCard.setName(String.valueOf(discountNum));
        }

        debitBalance = inputProductsForCheck.getDebitBalance();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public int getDebitBalance() {
        return debitBalance;
    }
}
