package ProofOfWork_SimplifiedBlockChain;

/**
 *
 * @author bryan
 */

public class Transaction {
    // Variables
    private final String sender;
    private final String receiver;
    private final String amount;
    private final String hash;
    
    private final SHA1 sha1 = new SHA1();
    
    // Constructor
    public Transaction(String sender, String receiver, String amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        
        this.hash = generateHash();
    }
    
    // Getter Methods
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getAmount() {
        return amount;
    }

    public String getHash() {
        return hash;
    }
    
    // Generate Hash Method
    private String generateHash(){
        return sha1.hash(sender + receiver + amount);
    }
    
    // toString Method
    @Override
    public String toString(){
        return String.format("%s:%s:%s", this.getSender(), this.getReceiver(), this.getAmount());
    }
    
}
