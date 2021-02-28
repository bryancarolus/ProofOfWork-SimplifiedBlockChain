package ProofOfWork_SimplifiedBlockChain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author bryan
 */

public class SHA1 {
    
    // Hash
    public String hash(String plaintext){
        return SHA1_Hash(plaintext);
    }
    
    // SHA-1 Method
    private String SHA1_Hash(String plaintext){
            String Hash = "";
        
            try{
                MessageDigest md = MessageDigest.getInstance("SHA-1");
        
                // Calculate message digest of an input and return array of byte
                byte[] messageDigest = md.digest(plaintext.getBytes());
                
                StringBuffer sb = new StringBuffer();
                
                for (int i = 0; i < messageDigest.length; i++) {
                    sb.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
                }
                
                return sb.toString();
            }
        
            catch(NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        
        }
}
