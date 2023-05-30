package FileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * TextFileReader reads from a given file and returns content
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class TextFileReader implements FileReader {
    private final String fileName;
    private List<String> data;

    /**
    * Class constructor specifying the path to the file
    * to be read.
    *
    * @param fileName relative or absolute path to the file
    */
    public TextFileReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> getData() {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            data = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                data.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("There is no file in the specified path");
            System.exit(-1);
        }
        return data;
    }
}
