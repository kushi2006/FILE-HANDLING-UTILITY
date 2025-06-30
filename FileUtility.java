package target.ChatApp;
import java.io.*;
import java.nio.file.*;

public class FileUtility {

    // Create a new file
    public static boolean createFile(String filePath) {
        try {
            File file = new File(filePath);
            return file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            return false;
        }
    }

    // Write to file (overwrites existing content)
    public static void writeFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Append to file
    public static void appendToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }

    // Read from file
    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return content.toString();
    }

    // Delete file
    public static boolean deleteFile(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }

    // Check if file exists
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    // Main method for demonstration
    public static void main(String[] args) {
        String filePath = "example.txt";

        // Create
        if (createFile(filePath)) {
            System.out.println("File created successfully.");
        } else {
            System.out.println("File already exists or failed to create.");
        }

        // Write
        writeFile(filePath, "Hello, this is the first line.\n");

        // Append
        appendToFile(filePath, "This is an appended line.\n");

        // Read
        String fileContent = readFile(filePath);
        System.out.println("File content:\n" + fileContent);

        // Delete
        if (deleteFile(filePath)) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("File deletion failed.");
        }
    }
}
