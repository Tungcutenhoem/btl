package Model;

import java.io.File;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ImageTextTranslator {
    public String ImageTextToText()  {
        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath("C:\\tudienn\\tessdata");
            File file = ChoiseFolder();
            String text = tesseract.doOCR(file);
            return text;
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "0";
    }
    public File ChoiseFolder() {
        FileChooser fileChooser = new FileChooser();

        // Thiết lập tiêu đề cho hộp thoại
        fileChooser.setTitle("Choise a picture");

        // Thiết lập bộ lọc chỉ cho phép chọn các tệp hình ảnh
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Hình ảnh", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile;
    }
}
