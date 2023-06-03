package example.ripManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatStorage {
    private static final String FILE_PATH = "chat_storage.txt";

    public static String getExistingString() {
        try {
            Path filePath = Paths.get(FILE_PATH);
            if (Files.exists(filePath)) {
                return Files.readString(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void saveString(String string) {
        try {
            Path filePath = Paths.get(FILE_PATH);
            Files.writeString(filePath, string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
