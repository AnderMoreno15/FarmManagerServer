/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Aitziber
 */
public class UserAuthService {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) {
        return BCrypt.checkpw(inputPassword, storedHash);
    }
}
