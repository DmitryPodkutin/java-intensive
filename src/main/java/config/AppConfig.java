package config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class AppConfig {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties");
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            log.error("Ошибка при загрузке application.properties", e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}