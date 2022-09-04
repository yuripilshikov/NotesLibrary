package libraryofnotes.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {

    Properties properties;
    public final static String URL = "url";
    public final static String USER = "user";
    public final static String PSW = "psw";

    public Settings() {
        properties = new Properties();
        File file = new File("settings.properties");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
