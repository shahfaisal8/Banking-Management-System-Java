
import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
public class Transaction implements Serializable{

    private static final long serialVersionUID = 1L;

    private String type;
    private double amount;
    private double afterAmount;
    private String timeStamp;


    Transaction(String type,double amount,double afterAmount){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.type = type;
        this.amount = amount;
        this.afterAmount = afterAmount;
        this.timeStamp = LocalDateTime.now().format(dtf);

    }

    void displayTransaction(){
        System.out.println("Type: "+type+" | Amount: "+amount+" | After Amount: "+afterAmount+" | Time: "+timeStamp);
    }
    
}
