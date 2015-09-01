package Lazorenko.View;

import Lazorenko.Controller.Controller;
import Lazorenko.Logger.LoggerToFile;

/**
 * Runnable class for application
 * @author andriylazorenko
 */

public class Main {
    public static void main(String[] args) {
        LoggerToFile loggerToFile = LoggerToFile.getInstance();
        loggerToFile.getLogger().fatal("Application running");
        Controller controller = new Controller();
        controller.run();
    }
}


