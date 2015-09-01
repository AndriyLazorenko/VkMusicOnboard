package Lazorenko.Model;

import Lazorenko.Logger.LoggerToFile;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Class used to handle data (loads properties)
 * @author andriylazorenko
 */

public class Model {

    /**
     * Variables
     */

    private Properties properties = new Properties();
    private LoggerToFile loggerToFile = LoggerToFile.getInstance();

    /**
     * Singleton constructor (loaded from file)
     */

    private volatile static Model uniqueInstance;

    public static Model getInstance(){
        if (uniqueInstance==null){
            synchronized (Model.class){
                if (uniqueInstance==null){
                    uniqueInstance = new Model();
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * Constructor loads properties from file
     */

    private Model() {
        try {
            this.properties.load(new FileReader("src/main/resources/Properties.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }
    }

    /**
     * Properties getter
     * @return
     */

    public Properties getProperties() {
        return properties;
    }

}
