package main.java.ru.clevertec.check.reader_writer;

import main.java.ru.clevertec.check.discountCards.CardsType;
import main.java.ru.clevertec.check.discountCards.DiscountCard;
import main.java.ru.clevertec.check.factory.DiscountCardFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DiscountCardReader {
    private static BufferedReader reader;
    private static final DiscountCardFactory discountCardFactory = new DiscountCardFactory();

    static {
        createFile();
    }

    private static void createFile () {
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/discountCards.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, DiscountCard> parseFile() {
        Map<Integer, DiscountCard> discountCardsMap = new HashMap<>();
        Scanner scanner = new Scanner(reader);
        scanner.nextLine();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            StringTokenizer tokenLine = new StringTokenizer(line, ";");
            tokenLine.nextToken();
            String discountCardName = tokenLine.nextToken();
            int numDiscountCard = Integer.parseInt(discountCardName);
            String discountCardType = "C" + discountCardName;
            double discountAmount = Double.parseDouble(tokenLine.nextToken());
            DiscountCard discountCard = discountCardFactory.getDiscountCard(CardsType.valueOf(discountCardType));
            discountCard.addValues(discountAmount, discountCardName);
            discountCardsMap.put(numDiscountCard, discountCard);
        }
        return discountCardsMap;
    }

}
