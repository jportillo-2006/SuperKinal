package org.jeffersonportillo.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    
    private static PasswordUtils instance;

    public PasswordUtils() {
    }
    
    public static PasswordUtils getInstance(){
        if(instance == null){
            instance = new PasswordUtils();
        }
        return instance;
    }
    
    public String encryptedPassword(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
    
    public boolean checkPassword(String pass, String encryptedPass){
        return BCrypt.checkpw(pass, encryptedPass);
    }  
}
