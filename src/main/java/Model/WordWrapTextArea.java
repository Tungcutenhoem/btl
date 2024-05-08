package Model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WordWrapTextArea extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea();

        // Đặt sự kiện key pressed cho TextArea
        textArea.setOnKeyPressed(event -> {
            // Kiểm tra khi phím Enter được ấn
            if (event.getCode() == KeyCode.ENTER) {
                // Lấy vị trí con trỏ hiện tại trong TextArea
                int caretPosition = textArea.getCaretPosition();
                // Lấy văn bản trong TextArea
                String text = textArea.getText();
                // Tìm vị trí của dòng hiện tại bắt đầu
                int lineStartIndex = text.lastIndexOf("\n", caretPosition - 1) + 1;
                // Tìm vị trí của dòng kế tiếp bắt đầu
                int nextLineStartIndex = text.indexOf("\n", caretPosition);
                if (nextLineStartIndex == -1) {
                    nextLineStartIndex = text.length();
                }
                // Lấy văn bản của dòng hiện tại
                String currentLine = text.substring(lineStartIndex, nextLineStartIndex);
                // Tìm vị trí của từ cuối cùng trong dòng
                int lastSpaceIndex = currentLine.lastIndexOf(" ");
                if (lastSpaceIndex != -1) {
                    // Lấy độ dài của từ cuối cùng
                    int lastWordLength = currentLine.length() - lastSpaceIndex - 1;
                    // Kiểm tra độ dài của từ cuối cùng
                    if (lastWordLength >= 10) {
                        // Chèn ký tự xuống dòng
                        textArea.insertText(caretPosition, "\n");
                    }
                }
            }
        });

        StackPane root = new StackPane(textArea);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Word Wrap TextArea");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
