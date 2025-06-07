/**
 * Creates the default accounts for ATM (used for testing). And calls the ATM
 * class to run the ATM
 * @author Lillian Thereault
 * @version June 2025
 */

import java.util.ArrayList;

public class Driver {
    /** default admin account for ATM **/
    private ArrayList<Integer> admin;
    /** default user account for ATM **/
    private ArrayList<Integer> user;
    /** 2D Array of all accounts attached to a bank/ATM **/
    private ArrayList<ArrayList<Integer>> bankInfo;

    public Driver() {
        this.admin = new ArrayList<>();
        this.admin.add(88888888);
        this.admin.add(4444);
        this.user = new ArrayList<>();
        this.user.add(12345678);
        this.user.add(1234);
    
        this.bankInfo = new ArrayList<>();
        this.bankInfo.add(admin);
        this.bankInfo.add(user);

        new ATM(this.bankInfo);
    }

    public static void main(String[] args) {
        new Driver();
    }
}
