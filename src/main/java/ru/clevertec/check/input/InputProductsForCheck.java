package main.java.ru.clevertec.check.input;

import main.java.ru.clevertec.check.discountCards.DefaultCard;
import main.java.ru.clevertec.check.discountCards.DiscountCard;
import main.java.ru.clevertec.check.exeptions.BadRequestException;
import main.java.ru.clevertec.check.products.Product;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProductsForCheck {
    private final String inputString;
    private final Map<Integer, Product> mapProduct;
    private final Map<Integer, DiscountCard> mapDiscountCard;

    private List<Product> productList;
    private DiscountCard discountCard = null;
    private int debitBalance;


    public InputProductsForCheck(String inputString, Map<Integer, Product> mapProduct, Map<Integer, DiscountCard> mapDiscountCard) throws BadRequestException {
        this.inputString = inputString;
        this.mapProduct = mapProduct;
        this.mapDiscountCard = mapDiscountCard;
        parseInputString();
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public int getDebitBalance() {
        return debitBalance;
    }

    private void parseInputString() throws BadRequestException {
        Pattern pairPattern = Pattern.compile("(\\d+)-(\\d+)");
        Matcher pairMatcher = pairPattern.matcher(inputString);
        Map<Integer, Product> newMapProduct = new HashMap<>();

        while (pairMatcher.find()) {
            int id = Integer.parseInt(pairMatcher.group(1));
            if (!mapProduct.containsKey(id)) throw new BadRequestException();
            Product productTmp = mapProduct.get(id);
            if (!newMapProduct.containsKey(id)) {
                newMapProduct.put(id, productTmp);
            }
            int value = Integer.parseInt(pairMatcher.group(2));
            productTmp.updateCountOfBuyingItems(value);
            newMapProduct.put(id, productTmp);
        }

        productList = new ArrayList<>(newMapProduct.values());

        Pattern cardPattern = Pattern.compile("discountCard=(\\d+)");
        Matcher cardMatcher = cardPattern.matcher(inputString);

        if (cardMatcher.find()) {
            int discountNum = Integer.parseInt(cardMatcher.group(1));
            if (mapDiscountCard.containsKey(discountNum)) discountCard = mapDiscountCard.get(discountNum);
            else discountCard = new DefaultCard();
        }

        Pattern balancePattern = Pattern.compile("balanceDebitCard=(\\d+)");
        Matcher balanceMatcher = balancePattern.matcher(inputString);

        if (balanceMatcher.find()) {
            debitBalance = Integer.parseInt(balanceMatcher.group(1));
        }
    }
}