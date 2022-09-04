package libraryofnotes.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class MyLogger {
    static MyLogger logger;
    private MyLogger(){}
    
    public static MyLogger getLogger() {
        if(logger == null) {
            logger = new MyLogger();
        }
        return logger;
    }
    
    public void info(String message) {
        File file = new File("logfile.log");
        try(FileWriter writer = new FileWriter(file, true);) {
            if(!file.exists()) {
                file.createNewFile();
            }
            Date date = new Date(System.currentTimeMillis());
            writer.write("\n" + date + " " + message);
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }    
}
