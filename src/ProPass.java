
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ivaylo
 */
public class ProPass {

    private static final Random R = new SecureRandom();
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 256;

    public static byte[] getSalt() {
        byte[] salt = new byte[16];
        R.nextBytes(salt);
        return salt;
        
    }    
    

    public static boolean isExpected(byte[] salt, char[] pass, String hashed){
        String toCheckHashed = genPass(salt, pass);
        if(hashed.equals(toCheckHashed)){
            return true;
        } else{
            return false;
        }
    }
    
    public static String genPass(byte[] salt, char[] pass){
        byte[] hashed = new byte[32];
        PBEKeySpec spec = new PBEKeySpec(pass, salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            hashed = key.generateSecret(spec).getEncoded();
            
            
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("No such algorithm: " + e.getMessage(), e);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(ProPass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            spec.clearPassword();
        }
        return String.format("%x", new BigInteger(hashed));
    }
    

}
