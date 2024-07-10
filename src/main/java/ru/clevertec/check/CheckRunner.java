package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.reader_writer.CheckWriter;

import java.io.IOException;
import java.util.Arrays;

public class CheckRunner {
    public static void main(String[] args) throws IOException {
        String inputString = Arrays.toString(args);
        CheckWriter checkWriter = new CheckWriter(inputString);
        checkWriter.writeInFile();
    }
}