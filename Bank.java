
import java.util.HashMap;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Bank implements Serializable {

    private String adminUsername = "admin";
    private String adminPassword = "admin123";
    

    public boolean adminAuthenticate(String admin,String pass){
        return adminUsername.equals(admin) && adminPassword.equals(pass);
    }

    private static final String FILE_NAME = "bank.dat";

    HashMap<Integer, Account> accounts; 
    

    Bank(){
       
        accounts = new HashMap<>();
         loadData();
    }

    void addAccount(Account acc){
        accounts.put(acc.getAccountno(),acc);
    }

    Account authenticate(int accNo,String pin){

    Account acc = accounts.get(accNo);

        if(acc != null && acc.validatePin(pin)){
           return acc;
        }

        return null;
}

    Account findAccount(int accNo){
      return accounts.get(accNo);
    }

    
    boolean transfer(int senderAcc,int receiverAcc,double amount){
        Account sender = findAccount(senderAcc);
        Account receiver = findAccount(receiverAcc);
        if(amount>0){

        if(sender == null || receiver == null ){
            System.out.println("Account Doesn't Exist.");
            return false;
        }else if(senderAcc == receiverAcc){
            System.out.println("Cannot transfer to Same Account.");
            return false;
        }else{
            boolean success = sender.withdraw(amount,"TRANSFER_OUT");
            if(success == true){
                receiver.deposit(amount,"TRANSFER_IN");
                return true;
            }else{
                return false;
            }


        }
        }else{
        
        return false;
        }
    }

    void showAllaccounts(){
         System.out.println("\nAccountNo | Name              | Balance | Status");

         for(Account acc : accounts.values()){

            String status = acc.getStatus() ? "LOCKED":"ACTIVE";

            System.out.println(
            acc.getAccountno() + " | " +
            acc.getName() + " | " +
            acc.getBalance() + " | " +
            status
        );

         }
    }

    void showTotalBalance(){

        double total = 0;

        for(Account acc:accounts.values()){

            total = total + acc.getBalance();


        }

        System.out.println("Total Balance: "+total+".");
    }

    void searchAccount(int accNum){

        for(Account acc: accounts.values()){
            int value = acc.getAccountno();

            if(value == accNum){
                acc.getAccountDetails();
            }
        }

        

    }

    void saveData(){

    try(
        ObjectOutputStream out =
            new ObjectOutputStream(
                new FileOutputStream(FILE_NAME)
            )
    ){

        out.writeObject(accounts);

        System.out.println("Data saved successfully.");

    } catch(Exception e){
        e.printStackTrace();
        System.out.println("Error saving data.");
    }

}

void loadData(){

    try(
        ObjectInputStream in =
            new ObjectInputStream(
                new FileInputStream(FILE_NAME)
            )
    ){

        accounts =
            (HashMap<Integer, Account>) in.readObject();

        System.out.println("Data loaded successfully.");
        int max = 1000;

for(Account acc : accounts.values()){
    if(acc.getAccountno() > max){
        max = acc.getAccountno();
    }
}

// set next account number
Account.setNextAccountNo(max + 1);

    } catch(Exception e){

        System.out.println(
        "No previous data found. Starting fresh."
        );

        accounts = new HashMap<>();
    }
}
    
}
