package main.java.ru.clevertec.check.reader_writer;

import main.java.ru.clevertec.check.exeptions.BadRequestException;
import main.java.ru.clevertec.check.factory.ProductFactory;
import main.java.ru.clevertec.check.products.Product;
import main.java.ru.clevertec.check.products.ProductType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ProductReader {
    private static BufferedReader reader;
    private static final ProductFactory productFactory = new ProductFactory();
    String pathToFile;

    public ProductReader(String pathToFile) throws BadRequestException {
        this.pathToFile = pathToFile;
        createFile();
    }

    private void createFile () throws BadRequestException {
        try {
            reader = new BufferedReader(new FileReader(pathToFile));
        } catch (FileNotFoundException | NullPointerException e) {
            throw new BadRequestException();
        }
    }

    public Map<Integer, Product> parseFile() {
        Map<Integer, Product> productMap = new HashMap<>();
        Scanner scanner = new Scanner(reader);
        scanner.nextLine();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            StringTokenizer tokenLine = new StringTokenizer(line, ";");
            int id = Integer.parseInt(tokenLine.nextToken());
            String productDescription = tokenLine.nextToken();
            String productName = normalizeProductName(productDescription);
            double price = Double.parseDouble(tokenLine.nextToken());
            int quantity = Integer.parseInt(tokenLine.nextToken());
            boolean wholesale = tokenLine.nextToken().equals("true");
            Product product = productFactory.getProduct(ProductType.valueOf(productName));
            product.addValues(price, quantity, wholesale, productDescription);
            productMap.put(id, product);
        }
        return productMap;
    }

    private String normalizeProductName(String productName) {
        productName = productName.replaceAll("\\s+","");
        int index = 0;
        if (productName.contains(".")) {
            index = productName.indexOf(".");
        } else if (productName.contains(",")) {
            index = productName.indexOf(",");
        } else return productName;
        productName = productName.substring(0, index) + productName.substring(index + 1);
        return productName;
    }
}