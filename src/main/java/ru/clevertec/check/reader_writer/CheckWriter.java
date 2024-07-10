package main.java.ru.clevertec.check.reader_writer;

import main.java.ru.clevertec.check.check_counter.CheckCounter;
import main.java.ru.clevertec.check.check_counter.ValueForWrite;
import main.java.ru.clevertec.check.discountCards.DiscountCard;
import main.java.ru.clevertec.check.exeptions.NotEnoughMoneyException;
import main.java.ru.clevertec.check.exeptions.BadRequestException;

import java.io.*;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckWriter {
    private static BufferedWriter writer;
    private final CheckCounter checkCounter;

    private static String outputFile = "result.csv";
    private static boolean fileChangerFlag = false;

    public CheckWriter(String inputString) {
        checkCounter = new CheckCounter(inputString);
        createFile();
    }

    public static void setOutputFile(String outputFile) {
        CheckWriter.outputFile = outputFile;
    }

    public static void setFileChangerFlag(boolean fileChangerFlag) {
        CheckWriter.fileChangerFlag = fileChangerFlag;
    }

    public static void createFile () {
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeInFile() throws IOException {
        try {
            checkCounter.countCheck();
            List<ValueForWrite> valueForWrites = checkCounter.getValueForWrites();

            if (fileChangerFlag) throw new BadRequestException();
            StringBuilder checkOutput = new StringBuilder();

            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Minsk"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            checkOutput.append("Data;Time" + "\n" + formattedDateTime + "\n\n");

            checkOutput.append("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");
            for (ValueForWrite valueForWrite : valueForWrites) {
                checkOutput.append(valueForWrite.toString() + "\n");
            }
            checkOutput.append("\n");

            DiscountCard discountCard = checkCounter.getDiscountCard();
            if (discountCard != null) {
                checkOutput.append("DISCOUNT CARD;DISCOUNT PERCENTAGE\n" + discountCard.getName() + ';' + (discountCard.getDiscountAmount() * 100) + "%\n\n");
            }

            checkOutput.append("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
            BigDecimal total = checkCounter.getCurrentAccount();
            BigDecimal totalDiscount = checkCounter.getCurrentDiscountSum();
            checkOutput.append(total + "$;" + totalDiscount + "$;" + (total.subtract(totalDiscount)) + "$");
            System.out.println(checkOutput);
            writer.write(String.valueOf(checkOutput));
            writer.close();

        } catch (BadRequestException e) {
            writer.write("ERROR\nBAD REQUEST");
            System.out.println("ERROR\nBAD REQUEST");
            writer.close();
        } catch (NotEnoughMoneyException e) {
            writer.write("ERROR\nNOT ENOUGH MONEY");
            System.out.println("ERROR\nNOT ENOUGH MONEY");
            writer.close();
        } catch (IOException/* | RuntimeException*/ e) {
            writer.write("INTERNAL SERVER ERROR");
            System.out.println("INTERNAL SERVER ERROR");
            writer.close();
        }
    }

}
