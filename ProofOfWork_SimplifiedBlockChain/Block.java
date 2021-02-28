package ProofOfWork_SimplifiedBlockChain;

import java.util.ArrayList;

/**
 *
 * @author bryan
 */

public class Block {
    // Variables
    private final String Block_ID; // Hash(transactionHash + Nonce + previousHash)
    private final String previousHash;
    private final Transaction transaction;
    private final long Nonce;
    
    private final SHA1 sha1 = new SHA1();
    
    // Constructors
    public Block(String Block_ID, String previousHash, Transaction transaction, long Nonce) {
        this.Block_ID = Block_ID;
        this.previousHash = previousHash;
        this.transaction = transaction;
        this.Nonce = Nonce;
    }
    
    // Getter Methods
    public String getBlock_ID() {
        return Block_ID;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getNonce() {
        return Nonce;
    }   
    
    public Transaction getTransaction() {
        return transaction;
    }
    
    @Override
    public String toString(){
        return String.format("%s:%s%n%s%n", "Block", this.getBlock_ID(), this.transaction);
    }
}
