/**
 * Creates the default accounts for ATM (used for testing). And calls the ATM
 * class to run the ATM
 * @author Lillian Thereault
 * @version June 2025
 */

import java.util.ArrayList;

/**
 * Creates fake user and admin for testing and simulation
 * @author Lillian Thereault
 */
public class Driver {

    public static void main(String[] args) {
        Account admin = new Account("88888888", "4444", true, "Admin");
        Account user = new Account("12345678", "1234", "John Doe");
        user.setAccountBalance(7000);


        ArrayList<Account> bank = new ArrayList<>();
        bank.add(admin);
        bank.add(user);

        new ATM(bank);
    }
}
