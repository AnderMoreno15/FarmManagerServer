/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Aitziber
 */
public class SymmetricEncryption {


/**
 * Criptografía Simétrica
 * 
 * Esta clase permite cifrar un texto y lo guarda en un fichero. La única forma 
 * de descifrar el texto es mediante una clave, que tanto el emisor como el 
 * receptor conocen.
 * 
 * PASOS: 
 * 1 Creacion de la clave, denominada derivada, porque se obtiene a partir de un password
 *         usando para ello la función PBKDF2  
 *         además añadimos un salt o semilla para hacerlo más random
 *         Hecho lo anterior usamos SecretKeyFactory para generar la clave simétrica
 *         y SecretKeySpec para "adaptarla" al algoritmo de cifrado: AES
 * 2 Empezamos el proceso de cifrado definiendo el algoritmoa usar: AES
 * 3 Como el AES cifra dividiendo el mensaje en bloques de 16 bytes definición del procedimiento de 
 * padding o relleno del ultimo bloque: PKCS5Padding
 * Para añadir más seguridad se utiliza el modo de operación CBC, o sea, XOR entre los bloques. Esto evita 
 * que se generen patrones, esto es, que dos bloques iguales al cifrar tengan el mismo aspecto 
 * 4 Cifrado. 
 * Añadimos un vector de inicialización para realizar el XOR del primer bloque con él
 */

    private static byte[] salt = "hola Bego!-Aitzi".getBytes(); 
    String path = getClass().getClassLoader().getResource("encryption/credentials.dat").getPath();

    public String cifrarTexto(String clave, String mensaje) throws java.security.InvalidKeyException {
        String ret = null;
        KeySpec derivedKey = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            derivedKey = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); 
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
             
            byte[] derivedKeyPBK = secretKeyFactory.generateSecret(derivedKey).getEncoded();
                     
            SecretKey derivedKeyPBK_AES = new SecretKeySpec(derivedKeyPBK, 0, derivedKeyPBK.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");	    
            cipher.init(Cipher.ENCRYPT_MODE, derivedKeyPBK_AES);
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes()); // Mensaje cifrado !!!
            byte[] iv = cipher.getIV(); // vector de inicializaci�n  
             
            // Añadimos el vector de inicialización
            byte[] combined = concatArrays(iv, encodedMessage);

            fileWriter(path, combined);

            ret = new String(encodedMessage);

        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            
        }
        return ret;
    }

    private String descifrarTexto(String clave) throws java.security.InvalidKeyException {
        String ret = null;
        byte[] fileContent = fileReader(path);
        
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            ret = new String(decodedMessage);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            
        }
        return ret;
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(text);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) throws InvalidKeyException {
        SymmetricEncryption ejemploAES = new SymmetricEncryption();
        String mensajeCifrado = ejemploAES.cifrarTexto("Clave", "Mensaje super secreto");
        System.out.println("Cifrado! -> " + mensajeCifrado);
        System.out.println("-----------");
        System.out.println("Descifrado! -> " + ejemploAES.descifrarTexto("Clave"));
        System.out.println("-----------");
    }
}
