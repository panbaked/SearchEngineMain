package SearchEnginePackage;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author rasmus
 */
public class MyLog {
    
    public static void logToFile(long s, String filename) throws IOException {
        Writer output = null;
        String logFile = filename;
        File file = new java.io.File(logFile);

        if(!file.exists()){
            System.out.println("\nNew log file created - " + logFile);
            output = new PrintWriter(file);
            //output.write("This log file was created on " + getDate() + "\n");
        }
        else{
            output = new BufferedWriter(new FileWriter(file, true));
            //output.write("\nThis was logged on " + getDate() + "\n");
        }
        output.write(s + "\n");
        output.close();
    }
    
    public static void logNothing(){
        System.out.println("Nothing has been logged");
    }
    
    private static String getDate() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());
        return dateNow;
    }
    
    
}
