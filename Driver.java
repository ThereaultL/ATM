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

//    public Driver() {
//        this.admin = new Account(88888888, 4444, true);
//        Account user = new Account(12345678, 1234);
//        user.setAccountBalance(7000);
//
//
//        this.bank = new ArrayList<>();
//        this.bank.add(admin);
//        this.bank.add(user);
//
//        new ATM(this.bank);
//    }

    /** Testing */
    public Driver() {
        this.admin = new Account(88888888, 4444, true);

        this.bank = new ArrayList<>();
        this.bank.add(admin);

        createUsers();

        new ATM(this.bank);
    }

    private void createUsers () {
        Account jack = new Account(12345678, 1234);
        jack.setAccountBalance(7000);
        Account jill = new Account(11223344, 9876);
        jill.setAccountBalance(500);
        Account david = new Account(10203040, 1020);

        this.bank.add(jack);
        this.bank.add(jill);
        this.bank.add(david);
    }

    public static void main(String[] args) {
        new Driver();
    }
}
