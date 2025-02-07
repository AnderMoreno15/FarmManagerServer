/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Aitziber
 */
public class UserAuthService {
    public static Logger logger = Logger.getLogger(UserAuthService.class.getName());

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) {
        logger.info("recibido en UserAuthService inputPassword: "+inputPassword);
        logger.info("recibido en UserAuthService storedHash: "+storedHash);
       return BCrypt.checkpw(inputPassword, storedHash);
    }
}
