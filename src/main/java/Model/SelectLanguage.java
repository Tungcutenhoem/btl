package Model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SelectLanguage {

    public SelectLanguage() {

    }

    private LanguageCode languageCode = new LanguageCode();
    private LanguageName languageName = new LanguageName();
    private SpeakCode speakCode = new SpeakCode();
    private SpeakName speakName = new SpeakName();
    private void getLanguageList() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\demo\\src\\main\\java\\Transition\\languages.txt");

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        try {
            while((line = bufferedReader.readLine()) != null)
            {
                String [] parts =line.split("\\s+", 4);
                if(parts.length == 4)
                {
                    String Language_Code = parts[0];
                    String Language_Name = parts[1];
                    String Speak_code = parts[2];
                    String Speak_Name = parts[3];

                    languageCode.AddAPIString(Language_Code);
                    languageName.AddAPIString(Language_Name);
                    speakCode.AddAPIString(Speak_code);
                    speakName.AddAPIString(Speak_Name);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<String> getLanguageName() throws IOException {
        getLanguageList();
        return languageName.getListAPI();
    }

    public ArrayList<String> getLanguageCode() throws IOException {
        getLanguageList();
        return languageCode.getListAPI();
    }

    public ArrayList<String> getSpeakName() throws IOException {
        getLanguageList();
        return speakName.getListAPI();
    }

    public ArrayList<String> getSpeakCode() throws IOException {
        getLanguageList();
        return speakCode.getListAPI();
    }

    public String translator(String langFrom, String langTo, String text) throws IOException {
        String urlApi = "https://script.google.com/macros/s/AKfycbz29fO5jCKw8Y-W0TePZLFM9jyI8urgaON2R9kMtC35l8dIl2aWQ9tqqYFnIkfERCI/exec"
                + "?q=" + URLEncoder.encode(text, "UTF-8")
                + "&target=" + langTo
                + "&source=" + langFrom;

        URL url = new URL(urlApi);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

        String input;
        StringBuilder response = new StringBuilder();

        while ((input = in.readLine()) != null) {
            response.append(input);
        }
        in.close();
        return response.toString();
    }

}
