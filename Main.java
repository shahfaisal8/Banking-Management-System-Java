import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank bank = new Bank();

        
       
        boolean mainRunning = true;

        while(mainRunning){

            System.out.println("\n==== BANKING SYSTEM ====");
            System.out.println("1. Open New Account");
            System.out.println("2. Login");
            System.out.println("3. Admin");
            System.out.println("4. Exit");


            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch(choice){

                // ------------------------
                // OPEN ACCOUNT
                // ------------------------
                case 1:

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Create 4-digit PIN: ");
                    String pin = sc.nextLine();

                    System.out.print("Opening Balance: ");
                    double openingBalance = sc.nextDouble();

                    if(openingBalance<500){
                        System.out.println("Please try later, Minimum Amount Required.");
                        mainRunning = false;
                    }else{

                    Account newAcc =
                        new Account(
                            name,
                            pin,
                            openingBalance
                        );

                    bank.addAccount(newAcc);

                    System.out.println(
                        "Account Created Successfully!"
                    );

                    System.out.println(
                        "Your Account Number: "
                        + newAcc.getAccountno()
                    );}

                    break;


                // ------------------------
                // LOGIN
                // ------------------------
                case 2:

                    System.out.print(
                    "Enter Account Number: "
                    );
                    int accNo=sc.nextInt();

                    System.out.print(
                    "Enter PIN: "
                    );
                    String enteredPin=sc.next();

                    Account user=
                       bank.authenticate(
                           accNo,
                           enteredPin
                       );

                    if(user==null){
                        System.out.println(
                         "Invalid Credentials."
                        );
                        break;
                    }


                    if(user.isLocked){
                        System.out.println("Your Account is Locked due to too many Attempts.");
                    }

                    if(user.validatePin(enteredPin)){
                    System.out.println(
                     "Login Successful!"
                    );

                    boolean loggedIn=true;

                    while(loggedIn){

                        System.out.println(
                        "\n---- USER MENU ----"
                        );

                        System.out.println(
                        "1. Balance"
                        );
                        System.out.println(
                        "2. Deposit"
                        );
                        System.out.println(
                        "3. Withdraw"
                        );
                        System.out.println(
                        "4. Transfer Funds"
                        );
                        System.out.println(
                        "5. Mini Statement"
                        );
                        System.out.println(
                        "6. Account Details"
                        );
                        System.out.println(
                        "7. Change PIN"
                        );
                        System.out.println(
                        "8. Log Out"
                        );


                        System.out.print("Choose: ");

                        int userChoice=
                           sc.nextInt();

                        switch(userChoice){

                            case 1:
                                user.showBalance();
                                break;

                            case 2:

                                System.out.print(
                                 "Deposit Amount: "
                                );

                                double dep=
                                  sc.nextDouble();

                                user.deposit(dep,"IN");

                                break;


                            case 3:

                                System.out.print(
                                "Withdraw Amount: "
                                );

                                double wd=
                                  sc.nextDouble();

                                user.withdraw(wd,"OUT");

                                break;


                            case 4:

                                System.out.print(
                                 "Receiver Account No: "
                                );

                                int receiver=
                                  sc.nextInt();

                                System.out.print(
                                 "Amount: "
                                );

                                double amt=
                                  sc.nextDouble();
                                  if(amt>0){

                                bank.transfer(
                                  user.getAccountno(),
                                  receiver,
                                  amt
                                );
                                  break;
                                }else{
                                    System.out.println("Invalid Amount.");
                                    break;
                                }

                                


                            case 5:

                                user.showStatements();

                                break;

                            
                            case 6:
                                user.getAccountDetails();
                                break;

                            case 7:
                                System.out.print("Enter Existing PIN: ");
                                String exPass = sc.next();
                                System.out.print("Enter New PIN: ");
                                String nePass = sc.next();
                                boolean status = user.changePin(exPass,nePass);

                                if(status){
                                    System.out.println("Success.");
                                }else{
                                    System.out.println("Logging Out...");
                                    break;
                                }
                                break;


                            case 8:

                                loggedIn=false;

                                System.out.println(
                                  "Logged out."
                                );

                                break;

                            


                            default:

                                System.out.println(
                                  "Invalid Option"
                                );
                        }

                    }}else{
                        System.out.println("Invalid PIN.");
                        break;
                    }

                    break;


                case 3:
    System.out.print("Username: ");
    String username = sc.next();

    System.out.print("Password: ");
    String password = sc.next();
    boolean entry = bank.adminAuthenticate(username,password);

   
    if (entry) {
        System.out.println("\nAccess Granted!");

        boolean running = true;
        while (running) {
            System.out.println("\n==== DASHBOARD ====");
            System.out.println("1. View All Accounts"); // Changed to println
            System.out.println("2. Find Account");
            System.out.println("3. View Total Bank Balance");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    bank.showAllaccounts();
                    break;
                case 2:
                    System.out.print("Enter Account No: ");
                    int no = sc.nextInt();
                    bank.searchAccount(no);
                    break;
                case 3:
                    bank.showTotalBalance();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
            }
        }
           } else {
              System.out.println("Invalid Admin Credentials!");
             }
              break; 




                    


                // ------------------------
                // EXIT
                // ------------------------
                case 4:
                    
                    bank.saveData();
                    mainRunning=false;

                    System.out.println(
                    "Thank You!"
                    );

                    break;


                default:
                    System.out.println(
                    "Invalid Choice"
                    );

            }

        }

        sc.close();
    }
}
                                                             
