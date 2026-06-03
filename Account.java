
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;


class Account implements Serializable{

    private static final long serialVersionUID = 1L;

    private double dailyWithDrawn;
    private LocalDate lastWithDrawnDate;
    double lastWithDrawn;
    double DAILY_LIMIT;


   


    ArrayList<Transaction> history;

    private static int nextAccountno = 1000;

    private int accountNo;
    private String name;
    private String PIN;
    private double balance;
    int noOfAttempts;
    boolean isLocked;

    Account(String name,String PIN,double balance){
        this.history = new ArrayList<>();
        this.accountNo = nextAccountno++;
        this.name = name;
        this.PIN = PIN;

        this.dailyWithDrawn = 0.0;
        this.lastWithDrawnDate = LocalDate.now(); 
        this.DAILY_LIMIT = 50000.0;
        
        if(balance <500){
            System.out.println("Minimum Balance should be 500.");
            this.balance = 0;
        }else{
            this.balance = balance;
        }
    }

    void deposit(double amount,String type){
        if(amount>0){
        this.balance += amount;
        history.add(new Transaction("TRANSFER_IN",amount,balance));
        }
        else{
            System.out.println("Invalid Amount!");
        }
    }

    boolean withdraw(double amount, String type) {
    
    if (lastWithDrawnDate == null) {
        lastWithDrawnDate = LocalDate.now();
    }

   
    if (!lastWithDrawnDate.equals(LocalDate.now())) {
        dailyWithDrawn = 0;
    }

    
    if (amount > balance) {
        System.out.println("Please enter a valid amount!");
        return false;
    } 
    
    
    if (dailyWithDrawn + amount > DAILY_LIMIT) {
    System.out.println("Daily withdrawal limit of " + DAILY_LIMIT + " exceeded.");
    return false;
    }else {
       
        balance = balance - amount;
        dailyWithDrawn += amount; 
        lastWithDrawnDate = LocalDate.now(); 
        
        System.out.println("Money Withdrawn!");
        history.add(new Transaction("TRANSFER_OUT", amount, balance));
        return true;
    }
}

    

    boolean changePin(String oldPin,String newPin) {
    if (isLocked) {
        System.out.println("Account is locked.");
        return false; 
    } 

    if(this.PIN.equals(newPin)){
        System.out.println("New PIN cannot be same as Old PIN.");
        return false;
    }

    if (this.PIN.equals(oldPin)) {
        
        this.PIN = newPin; 
        System.out.println("PIN changed successfully.");
        return true; 
    } else {
        System.out.println("Wrong current PIN.");
        return false;
    }
}
    

    void showStatements(){
        for(Transaction t:history){
            t.displayTransaction();
        }
    }

    void getAccountDetails(){
        System.out.println("Account Holder: "+name+" | Account Number: "+accountNo+" | Balance: "+balance);
    }

    void showBalance(){
        System.out.println("Balance Amount: "+balance);
    }

    int getAccountno(){
        return accountNo;
    }

    String getPIN(){
        return PIN;
    }

    String getName(){
        return name;
    }

    double getBalance(){
        return balance;
    }

    boolean getStatus(){
        return isLocked;
    }

    

    public boolean validatePin(String inputPIN){

        if(isLocked){
            return false;
        }

        if(this.PIN.equals(inputPIN)){
           this.noOfAttempts = 0;
            return true;
        }
        else{
            this.noOfAttempts++;
            if(this.noOfAttempts == 3){
                this.isLocked = true;
                System.out.println(
            "Account locked after 3 failed attempts."
            );
                
                
            }
            return false;

        }

    }

    public static void setNextAccountNo(int next){
    nextAccountno = next;
}

}