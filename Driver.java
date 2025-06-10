/**
 * Creates the default accounts for ATM (used for testing). And calls the ATM
 * class to run the ATM
 * @author Lillian Thereault
 * @version June 2025
 */

import java.util.ArrayList;

public class Driver {
    /** default admin account for ATM **/
    private Account admin;
    /** 2D Array of all accounts attached to a bank/ATM **/
    private ArrayList<Account> bank;

    public Driver() {
        this.admin = new Account(88888888, 4444, true);
        Account user = new Account(12345678, 1234);
        user.setAccountBalance(7000);


        this.bank = new ArrayList<>();
        this.bank.add(admin);
        this.bank.add(user);

        new ATM(this.bank);
    }

    public static void main(String[] args) {
        new Driver();
    }
}
