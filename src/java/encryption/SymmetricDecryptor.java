/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import config.ConfigReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Aitziber
 */
public class SymmetricDecryptor {
    public static Logger logger = Logger.getLogger(SymmetricDecryptor.class.getName());
    
    public static SecretKeySpec loadAESKey() throws IOException {
        String keyString = ConfigReader.getKey();
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, "AES");
    }
    
    public static String decrypt(String encryptedData) throws Exception {
        try {
            logger.info("Starting decryption...");

            if (encryptedData == null || encryptedData.isEmpty()) {
                logger.severe("Error: Encrypted data is null or empty.");
                return null;
            }
            logger.info("Encrypted data received: " + encryptedData);

            byte[] decodedBytes;
            try {
                decodedBytes = Base64.getDecoder().decode(encryptedData);
                logger.info("Base64 decoded. Size: " + decodedBytes.length);
            } catch (IllegalArgumentException e) {
                logger.severe("Error decoding Base64: " + e.getMessage());
                return null;
            }

            SecretKey key = loadAESKey();
            if (key == null) {
                logger.severe("Error: AES key is null.");
                return null;
            }
            logger.info("AES key loaded successfully.");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, key);
            logger.info("Cipher initialized in decryption mode.");

            byte[] decryptedBytes;
            try {
                decryptedBytes = cipher.doFinal(decodedBytes);
                logger.info("Decryption completed. Size: " + decryptedBytes.length);
            } catch (Exception e) {
                logger.severe("Error in cipher.doFinal(): " + e.getMessage());
                return null;
            }

            String result = new String(decryptedBytes, StandardCharsets.UTF_8);
            if (result.isEmpty()) {
                logger.severe("Error: The decrypted text is empty.");
                return null;
            }

            logger.info("Decrypted text: " + result);
            return result;

        } catch (Exception e) {
            logger.severe("Unexpected error in decrypt(): " + e.getMessage());
            return null;
        }
    }
}
