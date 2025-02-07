/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Aitziber
 */
public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar el archivo config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error al cargar el archivo de propiedades", ex);
        }
    }

    public static String getKey() {
        return properties.getProperty("key");
    }
}
