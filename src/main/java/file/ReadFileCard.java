package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileCard {

    public String readLines() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("Card.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            System.out.println("Такого файла нет");
        }
        return stringBuilder.toString();
    }
}
