package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.reader_writer.CheckWriter;

import java.io.IOException;

public class CheckRunner {
    public static void main(String[] args) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            inputStringBuilder.append(args[i]).append(" ");
        }
        CheckWriter checkWriter = new CheckWriter(String.valueOf(inputStringBuilder));
        checkWriter.writeInFile();
    }
}