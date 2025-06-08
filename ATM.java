/**
 * Description Here
 * @author Lillian Thereault
 * @version June 2025
 */

import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    /** Scanner to scan in data from STDOUT */
    private Scanner scanIn;
    /** 2D Array of all accounts attached to a bank/ATM */
    private ArrayList<Account> bankInfo;
    /** index of the current account being used on ATM */
    private int activeAccount;
    private boolean isAccountActive;

    public ATM(ArrayList<Account> bankInfo) {
        this.scanIn = new Scanner(System.in);
        this.bankInfo = bankInfo;
        this.activeAccount = -1;
        welcomeMenu();
    }

    private void welcomeMenu() {
        enterAccountNum();
        enterAccountPin();
    }

    private void enterAccountNum() {
        System.out.println(
                "Welcome!\nPlease enter your account number on the pin pad"
        );
        boolean condition = false;
        while(!condition) {
            try {
                Integer num = this.scanIn.nextInt();
                for (int i = 0; i < bankInfo.size(); i++) {
                    if(bankInfo.get(i).getAccountNumber() == num) {
                        activeAccount = i;
                        condition = true;
                    }
                }
               if (activeAccount == -1) {
                    System.out.println(
                            "Error: re-enter account number"
                    );
                }
            } catch (IllegalArgumentException err) {
                System.out.println(
                        "Account number must be an integer between 8-12 " +
                        "digits!"
                );
            }
        }
    }

    private void enterAccountPin() {
        System.out.println("Please enter your personal pin on the pin pad");
        boolean condition = false;
        while(!condition) {
            try {
                int num = this.scanIn.nextInt();
                if (bankInfo.get(activeAccount).getAccountPin() == num) {
                    isAccountActive = true;
                    condition = true;
                } else {
                    System.out.println(
                            "Error: re-enter personal pin"
                    );
                }
            } catch (IllegalArgumentException err) {
                System.out.println(
                        "Pin must be an integer between 8-12 digits!"
                );
            }
        }
    }

    private void userMainMenu() {
        System.out.println(
                "\n====================\n1: Check Available Balance\n2: " +
                "Withdraw\n3: Deposit\n4: View Statement\n0: Exit\n=========" +
                "\n==========="
        );
        try {
            int option = this.scanIn.nextInt();
            boolean condition = false;
            while(!condition) {
                System.out.println(
                        "Please enter an option using the pin pad: "
                );
                if (option == 1) {

                } else if (option == 2) {

                } else if (option == 3) {

                } else if (option == 4) {

                } else if (option == 0) {
                    activeAccount = -1;
                    welcomeMenu();
                    condition = true;
                }
            }
        } catch (IllegalArgumentException err) {
            System.out.println(
                    "Error: Please enter an integer");
        }
    }

    private void printAdminMainMenu() {

    }
}
