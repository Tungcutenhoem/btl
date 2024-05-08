package Controller;

import com.example.demo.Word;

import java.io.*;
import java.util.List;
import java.util.Map;

public class MainCommant {
    public static void removeWordFromFile(String wordToRemove, String filePath) {
        File tempFile = new File("C:\\tudienn\\src\\main\\java\\Transition\\tempUserSaveWord.txt");
        File filecurent = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filecurent));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(wordToRemove)) {
                    writer.write(line + System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(filecurent)) {
            // Ghi một chuỗi rỗng để xóa hết dữ liệu trong tệp
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Xóa file cũ

        filecurent.delete();
        // Đổi tên file tạm thành tên file gốc
        tempFile.renameTo(filecurent);
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filecurent))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(wordToRemove)) {
                    writer.write(line + System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(tempFile)) {
            // Ghi một chuỗi rỗng để xóa hết dữ liệu trong tệp
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
