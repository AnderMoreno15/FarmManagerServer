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
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Aitziber
 */
public class SymmetricDecryptor {
    public static Logger logger = Logger.getLogger(SymmetricDecryptor.class.getName());
    
    public static SecretKeySpec loadAESKey() throws IOException {
        logger.info("loadingkey");
        String keyString = ConfigReader.getKey();
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, "AES");
    }
    
    public static String decrypt(String encryptedData) throws Exception {
        logger.info("decrypt");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, loadAESKey());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        logger.info(new String(decryptedBytes, StandardCharsets.UTF_8));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
