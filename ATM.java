/**
 * Description Here
 * @author Lillian Thereault
 * @version June 2025
 */

import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    /** Scanner to scan in data from STDOUT */
    private final Scanner scanIn;
    /** 2D Array of all accounts attached to a bank/ATM */
    private ArrayList<Account> bankInfo;
    /** index of the current account being used on ATM */
    private Account activeAccount;
    /** called to see whether a user is active or not */
    private boolean isAccountActive;
    /** The current balance inside the ATM */
    private int machineBalance;
    private String machineStatement;

    /**
     * Creates an ATM object using an array of accounts, setting the ATM's
     * balance to zero before starting opening the machines welcome menu
     * @param bankInfo array of Account objects
     */
    public ATM(ArrayList<Account> bankInfo) {
        this.scanIn = new Scanner(System.in);
        this.bankInfo = bankInfo;
        this.machineBalance = 0;
        this.machineStatement = "";
        welcomeMenu();
    }

    /**
     * Sets the active account to -1, meaning there is currently no active
     * user, before prompting for user to enter their account number before
     * entering their personal pin
     */
    private void welcomeMenu() {
        System.out.println(
                "\nWelcome!\nPlease enter your account number on the pin pad"
        );
        this.activeAccount = enterAccountNum();
        enterAccountPin();
    }

    /**
     * Prompts the user to enter their account number into the machine to
     * identify the user
     * Ensures user types in a valid account number associated with
     * the corresponding bank
     * Only allows for integer input
     */
    private Account enterAccountNum() {
        boolean condition = false;
        Account acc = null;
        while(!condition) {
            try {
                String num = this.scanIn.next();
                for (Account account : this.bankInfo) {
                    //check if account number exists inside bankInfo
                    if (account.getAccountNumber().equals(num)) {
                        acc = account;
                        condition = true;
                    }
                }
                //account did not exist inside bankInfo
               if (!condition) {
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
        return acc;
    }

    /**
     * Prompts the user to enter their account pin into the machine to
     * verify the users logon
     * Ensures user types in a valid pin associated with the user account
     * number
     * Only allows for integer input
     */
    private void enterAccountPin() {
        System.out.println("Please enter your personal pin on the pin pad");
        boolean condition = false;
        while(!condition) {
            try {
                String num = this.scanIn.next();
                //Using the active account, compare the pin entered with
                //account pin
                if (this.activeAccount.getAccountPin().equals(num)) {
                    mainMenu();
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

    private void addStatement(boolean deposit, int amt) {
        StringBuilder result = new StringBuilder();
        result.append(this.activeAccount.getFullName()).append(deposit ? "deposit" : "withdraw");
        result.append(" $").append(amt).append("\n");
        this.machineStatement += result.toString();
    }

    /**
     * Determines if the active account is an admin, or user, and calls the
     * respective menu for the account type
     */
    private void mainMenu () {
        if(this.activeAccount.getIsAdmin()) {
            adminMainMenu();
        } else {
            userMainMenu();
        }
    }

    /**
     * Prompts the user with 5 (keys 0-4) options, check available balance,
     * withdraw, deposit, transfer funds, and exit. If user enters an integer
     * which does not exist on the menu, they will be prompted to re-enter
     * an option until correct usage. Does not allow for non-int datatypes
     */
    private void userMainMenu() {
        try {
            boolean condition = false;
            while(!condition) {
                System.out.println(
                        "\n========" + this.activeAccount.getFullName() + "========\n1: Check Available Balance\n2: " +
                        "Withdraw\n3: Deposit\n4: Transfer Funds\n0: Exit\n=========" +
                        "===========\nPlease enter an option using the pin pad: "
                );
                int option = this.scanIn.nextInt();

                if (option == 1) {
                    System.out.println(
                            this.activeAccount.toString()
                    );
                } else if (option == 2) {
                    withdraw();
                } else if (option == 3) {
                    deposit();
                } else if (option == 4) {
                    transferFunds();
                } else if (option == 0) {
                    welcomeMenu();
                    condition = true;
                }
            }
        } catch (IllegalArgumentException err) {
            System.out.println(
                    "Error: Please enter an integer"
            );
        }
    }

    /**
     * Prompts user to enter the dollar amount which they would like to
     * withdraw from their account, handles for integers only. Checks
     * the balance of their account, and the machine to ensure user can
     * withdraw the desired ammount. Prints corresponding error message
     * if withdraw can not be completed. When successfully withdrew,
     * the amount will be subtracted from the total machine balance
     */
    private void withdraw () {
        int amt = 0;
        boolean condition = false;
        while(!condition) {
            try {
                System.out.println(
                        "\n-----Withdraw-----\nEnter withdraw amount:"
                );
                amt = scanIn.nextInt();
                //checks if machine carries desired amount
                if(this.machineBalance >= amt) {
                    //checks if account can withdraw desired amount
                    int success = this.activeAccount.decrementBalance(amt);
                    condition = canWithdraw(success, amt);
                } else {
                    System.out.println(
                            "\n-----Machine has Insufficient Funds-----\n" +
                            "Please contact machine admin for assistance"
                    );
                }

            } catch (IllegalArgumentException err) {
                System.out.println(
                        "Error: Please enter an integer"
                );
            }
        }
        addStatement(false, amt);
    }

    /**
     * Determines if the user has the sufficient funds in their account
     * to withdraw a desired amount
     * @param success 1 if sufficient funds, 0 if insufficient funds
     * @param amt amount to withdraw
     * @return true/false
     */
    private boolean canWithdraw (int success, int amt) {
        boolean result = false;
        if(success == -1) {
            System.out.println(
                    "Error: Insufficient Funds"
            );
        } else {
            System.out.println(
                    "Successfully withdrew $" + amt +
                            "\nNew balance: $" + this.activeAccount.getAccountBalance()
            );
            result = true;
        }
        return result;
    }

    /**
     * Prompts the user to enter the desired amount in which they would like
     * to deposit to their account in cash, must be an integer. When
     * successfully deposited to account, the amount is added to the total
     * machine balance.
     */
    private void deposit() {
        try {
            System.out.println(
                    "\n-----Deposit-----\nEnter you deposit amount before inserting cash:"
            );
            int amt = scanIn.nextInt();
            this.activeAccount.incrementBalance(amt);
            System.out.println(
                    "Successfully deposited $" + amt +
                    "\nNew balance: $" +
                    this.activeAccount.getAccountBalance()
            );
            this.machineBalance += amt;
            addStatement(true, amt);
        } catch (IllegalArgumentException err) {
            System.out.println("Error: Please enter an integer");
        }
    }

    /**
     * Prompts user to enter the account number which they would like to transfer funds to. Once
     * confirmed the account exists within the bank, user will be asked if they would like to
     * transfer from their account, or with cash. Each prompt user will be asked to typed desired
     * amount on the pin pad, before asked to re-enter their account pin. Handles only integer
     * datatype
     */
    private void transferFunds () {
        System.out.println("Please enter the account number you would like to transfer to");
        Account transferTo = enterAccountNum();
        //prompt user to re-enter their personal pin
        //prompt user to enter amount they would like to transfer
        //withdraw amount from active account, deposit into transfer account
    }

    private void adminMainMenu() {
        try {
            boolean condition = false;
            while (!condition) {
                System.out.println(
                        "\n========Admin========\n1: Check Machine Balance\n2: " +
                        "Machine Withdraw\n3: Machine Deposit\n4: Print Machine Statement\n0:"
                        + " Exit\n=====================\n\nPlease enter an option using the pin pad: "
                );
                int option = this.scanIn.nextInt();

                if (option == 1) {
                    checkMachineBalance();
                } else if (option == 2) {
                    machineWithdraw();
                } else if (option == 3) {
                    machineDeposit();
                } else if (option == 4) {
                    printStatement();
                } else if (option == 0) {
                    welcomeMenu();
                    condition = true;
                }
            }
        } catch (IllegalArgumentException err) {
            System.out.println(
                    "Error: Please enter an integer"
            );
        }
    }

    private void checkMachineBalance() {
        System.out.println(
                "\n-----Check Machine Balance-----\n Machine Balance: $" +
                this.machineBalance
        );
    }

    private void machineWithdraw () {
        int amt = 0;
        boolean condition = false;
        while(!condition) {
            try {
                System.out.println(
                        "\n-----Machine Withdraw-----\nEnter withdraw amount:"
                );
                amt = scanIn.nextInt();
                if(this.machineBalance >= amt) {
                    this.decrementMachineBalance(amt);
                    condition = true;
                } else {
                    System.out.println(
                            "\n-----Machine has Insufficient Funds-----\n" +
                                    "Please contact machine admin for assistance\n"
                    );
                }

            } catch (IllegalArgumentException err) {
                System.out.println(
                        "Error: Please enter an integer"
                );
            }
        }
        addStatement(false, amt);
    }

    private void machineDeposit () {
        try {
            System.out.println(
                    "\n-----Machine Deposit-----\nEnter you deposit amount " +
                    "before inserting cash:"
            );
            int amt = scanIn.nextInt();
            this.incrementMachineBalance(amt);
            System.out.println(
                    "Successfully deposited $" + amt +
                            "\nNew balance: $" +
                            this.machineBalance
            );
            addStatement(true, amt);
        } catch (IllegalArgumentException err) {
            System.out.println("Error: Please enter an integer");
        }
    }

    private void incrementMachineBalance (int amt) { this.machineBalance += amt; }

    private void decrementMachineBalance (int amt) { this.machineBalance -= amt; }

    private void printStatement () {
            checkMachineBalance();
            System.out.println(this.machineBalance);
    }
}
