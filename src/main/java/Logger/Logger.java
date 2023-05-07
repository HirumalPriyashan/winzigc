package Logger;

/**
 * Class representation for Logger which handles console log 
 * outputs
 * 
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class Logger {
    private static boolean isEnabled = true;

    /**
     * Enables logger
     */
    public static void enableLogger(){
        isEnabled = true;
    } 
    
    /**
     * Disables logger
     */
    public static void disableLogger(){
        isEnabled = false;
    }
    
    /**
     * Log the given object in to console and add a new line
     * 
     * @param toPrint object to be logged
     */
    public static void log(Object toPrint){
        if (isEnabled) {
            System.out.println(toPrint);
        }
    }

    /**
     * Log a new line in to the console
     */
    public static void logNewLine(){
        if (isEnabled) {
            System.out.println();
        }
    }
    
    /**
     * Log the given object in to console without adding a new line
     * 
     * @param toPrint object to be logged
     */
    public static void logInLine(Object toPrint){
        if (isEnabled) {
            System.out.print(toPrint);
        }
    }
}
