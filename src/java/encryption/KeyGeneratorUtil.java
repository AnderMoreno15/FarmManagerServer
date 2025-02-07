/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Aitziber
 */
public class KeyGeneratorUtil {
        private static final String KEY_FILE = "C:\\Users\\Usuario\\Documents\\secret.key";


    public static void main(String[] args) {
        try {
            generateAndSaveKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateAndSaveKey() throws Exception {
        // Generar clave AES de 256 bits
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // Puedes cambiar a 128 o 192 bits si es necesario
        SecretKey secretKey = keyGen.generateKey();

        // Convertir la clave a Base64
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        
        // Guardar clave en un archivo
        saveKeyToFile(KEY_FILE, encodedKey);

        System.out.println("Clave secreta AES generada y guardada en: " + KEY_FILE);
    }

    private static void saveKeyToFile(String fileName, String key) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(key.getBytes());
        }
    }

    public static String readKeyFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(KEY_FILE)));
    }
}