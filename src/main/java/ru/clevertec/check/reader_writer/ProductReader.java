package main.java.ru.clevertec.check.reader_writer;

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

    static {
        createFile();
    }

    private static void createFile () {
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/products.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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