package file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFileCheck {
    public void WriteToFile(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("ResultCheck.txt", true));
        writer.append(text + "\n");
        writer.close();
    }

}
