package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is the class to read from the configuration file.
 */
public class YamlReader {

    private static final String FAILED_READ_PROPERTIES_ERROR_MESSAGE = "Failed to read properties";
    /**
     * Thread-safe property class. It is a subclass of Hashtable.
     */
    private static Properties properties;
    /**
     * Path to the configuration file.
     */
    public static final String PATH_TO_PROPERTIES = "src/main/resources/application.yml";

    public YamlReader() {
    }

    /**
     * This is a method for getting values ​​from a file by key.
     * @param key This is the key by which the value is found.
     * @return the value from the file.
     */
    public static String get(String key) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(key);
    }

    /**
     * This is the method for connecting to the configuration class.
     */
    private static void loadProperties() {
        try (InputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println(FAILED_READ_PROPERTIES_ERROR_MESSAGE + e.getMessage());
        }
    }
}