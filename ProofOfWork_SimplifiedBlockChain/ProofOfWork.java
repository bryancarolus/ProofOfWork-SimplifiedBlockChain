package ProofOfWork_SimplifiedBlockChain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author bryan
 */

public class ProofOfWork {
    
    private static int limit;
    private static String transactions_filename;
    private static String ledger_filename;
    private static final String SECRETFILE = "secret.txt";
    
    private static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        System.out.println("Simplified Block Chain - Proof Of Work");
        System.out.println("-------------------------------------------------");
        System.out.print("Input Ledger Filename: ");
        ledger_filename = input.nextLine();
        System.out.print("Input Transactions Filename: ");
        transactions_filename = input.nextLine();
        System.out.print("Input the number of leading zeros (1-4): ");
        limit = input.nextInt();
        
        if(limit > 4 || limit < 1){
            System.out.println("Error - Value is invalid");
            System.exit(0);
        }
        
        File ledgerFile = new File(ledger_filename);
        File transactionsFile = new File(transactions_filename);
        
        // Read Files
        ArrayList <String> newLedger = new ArrayList <>();
        ArrayList <String> newTransactions = new ArrayList <>();
        
        try{
            readFile(newLedger, ledgerFile);
            readFile(newTransactions, transactionsFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Error - FileNotFoundException");
            System.exit(0);
        }
        
        // Create first block object
        String[] firstBlock = newLedger.get(0).split(":");
        String[] firstTransaction = newLedger.get(1).split(":");
        
        Transaction Transaction1 = new Transaction(firstTransaction[0], firstTransaction[1], firstTransaction[2]);
        Block Block1 = new Block(firstBlock[1], "0", Transaction1, 0);
        
        // Mining
        SHA1 sha1 = new SHA1();
        ArrayList <Block> BlockChain = new ArrayList <>();
        
        BlockChain.add(Block1);
        
        for (int i = 0; i < newTransactions.size(); i++) {
            String[] transactionDetails = newTransactions.get(i).split(":");
            String sender = transactionDetails[0];
            String receiver = transactionDetails[1];
            String amount = transactionDetails[2];
            
            Transaction t = new Transaction(sender, receiver, amount);
            
            String allZeros = "0000000000000000000000000";
            
            long nonceGenerated = 0;
            String hashValue = "";
            String previousHash = BlockChain.get(i).getBlock_ID();
            
            int counter = 0;
            
            do{
                nonceGenerated++;
                hashValue = sha1.hash(t.getHash() + nonceGenerated + previousHash);
                
                if(counter % 3 == 0)
                    System.out.println();
                
                System.out.print(hashValue);
                
                counter++;
                
            }while(!hashValue.startsWith(allZeros.substring(0, limit)));
            
            BlockChain.add(new Block(hashValue, previousHash, t, nonceGenerated));
        }
        
        // Write to Ledger.txt and Secret.txt
        FileWriter ledgerWriter = new FileWriter(new File("New_Ledger_File.txt"));
        FileWriter secretWriter = new FileWriter(new File(SECRETFILE));
        String toLedger = "";
        String toSecret = "";
        
        for(Block b : BlockChain){
            toLedger = toLedger + b;
        }
        
        for (int i = 1; i < BlockChain.size(); i++) {
            toSecret = toSecret + BlockChain.get(i).getTransaction() + "\n" + BlockChain.get(i).getNonce() + "\n";   
        }
        
        ledgerWriter.write(toLedger);
        secretWriter.write(toSecret);
        
        ledgerWriter.flush();
        secretWriter.flush();
        ledgerWriter.close();
        secretWriter.close();
        
        // Print Output
        System.out.println();
        System.out.println();
        System.out.println("First Block : " + BlockChain.get(0).getBlock_ID());
        System.out.println();
        System.out.printf("%-55s%-55s%-30s%n", "Transaction", "Block ID (Hash Value)", "Nonce");
        System.out.println("-------------------------------------------------------------------"
                + "----------------------------------------------------------------------------");
        for (int i = 1; i < BlockChain.size(); i++) {
            System.out.printf("%-55s%-55s%-30d%n", 
                    BlockChain.get(i).getTransaction(), BlockChain.get(i).getBlock_ID(), BlockChain.get(i).getNonce());  
        }
        
        System.out.println();
        
        System.out.println("New_Ledger_File.txt Created Successfully");
        System.out.println(SECRETFILE + " Created Successfully");
    }
    
    private static void readFile(ArrayList<String> newFile, File file) throws FileNotFoundException{
        Scanner sc = new Scanner(file);
        
        while(sc.hasNextLine()){
            newFile.add(sc.nextLine());
        }  
    }
    
    
}
