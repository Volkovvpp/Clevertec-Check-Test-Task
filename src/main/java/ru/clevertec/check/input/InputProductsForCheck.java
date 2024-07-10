package main.java.ru.clevertec.check.input;

import main.java.ru.clevertec.check.exeptions.BadRequestException;
import main.java.ru.clevertec.check.reader_writer.CheckWriter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProductsForCheck {
    private String inputString;

    private Map<Integer, Integer> mapProduct = new HashMap<>();

    private Integer discountCardNum = null;

    private double debitBalance;
    private String pathToFile;
    private String saveToFile;

    public InputProductsForCheck(String inputString) throws BadRequestException {
        this.inputString = inputString;
        parseInputString();
    }

    public Map<Integer, Integer> getMapProduct() {
        return mapProduct;
    }

    public Integer getDiscountCardNum() {
        return discountCardNum;
    }

    public double getDebitBalance() {
        return debitBalance;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    private void parseInputString() throws BadRequestException {
        Pattern pairPattern = Pattern.compile("(\\d+)-(\\d+)");
        Matcher pairMatcher = pairPattern.matcher(inputString);

        while (pairMatcher.find()) {
            int id = Integer.parseInt(pairMatcher.group(1));
            int value = Integer.parseInt(pairMatcher.group(2));
            if (!mapProduct.containsKey(id)) {
                mapProduct.put(id, value);
            } else {
                int valueTmp = mapProduct.get(id) + value;
                mapProduct.put(id, valueTmp);
            }
        }

        Pattern cardPattern = Pattern.compile("discountCard=(\\d+)");
        Matcher cardMatcher = cardPattern.matcher(inputString);

        if (cardMatcher.find()) {
            discountCardNum = Integer.parseInt(cardMatcher.group(1));
        }

        Pattern balancePattern = Pattern.compile("balanceDebitCard=(-?\\d+(\\.\\d+)?)");
        Matcher balanceMatcher = balancePattern.matcher(inputString);

        if (balanceMatcher.find()) {
            debitBalance = Double.parseDouble(balanceMatcher.group(1));
        }

        Pattern pathToFilePattern = Pattern.compile("pathToFile=(\\S+)");
        Matcher pathToFileMatcher = pathToFilePattern.matcher(inputString);

        if (pathToFileMatcher.find()) {
            pathToFile = pathToFileMatcher.group(1);
        }

        Pattern saveToFilePattern = Pattern.compile("saveToFile=(\\S+)");
        Matcher saveToFileMatcher = saveToFilePattern.matcher(inputString);

        if (saveToFileMatcher.find()) {
            saveToFile = saveToFileMatcher.group(1);
            CheckWriter.setOutputFile(saveToFile);
            CheckWriter.createFile();
        } else {
            CheckWriter.setFileChangerFlag(true);
        }
    }
}