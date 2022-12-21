package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileProduct {
    public String readLines() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("ProductList.txt"))) {
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
