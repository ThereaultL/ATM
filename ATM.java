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
    private int machineBalance;

    public ATM(ArrayList<Account> bankInfo) {
        this.scanIn = new Scanner(System.in);
        this.bankInfo = bankInfo;
        this.machineBalance = 0;
        welcomeMenu();
    }

    private void welcomeMenu() {
        this.activeAccount = -1;
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
                int num = this.scanIn.nextInt();
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

    private void mainMenu () {
        if(this.bankInfo.get(this.activeAccount).getIsAdmin()) {
            adminMainMenu();
        } else {
            userMainMenu();
        }
    }

    private void userMainMenu() {
        System.out.println(
                "\n====================\n1: Check Available Balance\n2: " +
                "Withdraw\n3: Deposit\n4: View Statement\n0: Exit\n=========" +
                "===========\n"
        );
        try {
            int option = this.scanIn.nextInt();
            boolean condition = false;
            while(!condition) {
                System.out.println(
                        "Please enter an option using the pin pad: "
                );
                if (option == 1) {
                    System.out.println(
                            this.bankInfo.get(this.activeAccount).toString()
                    );
                } else if (option == 2) {
                    withdraw();
                } else if (option == 3) {
                    deposit();
                } else if (option == 4) {
                    viewStatement();
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

    private void withdraw () {
        boolean condition = false;
        while(!condition) {
            try {
                System.out.println(
                        "\n-----Withdraw-----\nEnter withdraw amount:"
                );
                int amt = scanIn.nextInt();
                if(this.machineBalance >= amt) {
                    int success = this.bankInfo.get(
                            this.activeAccount
                    ).decrementBalance(amt);
                    condition = canWithdraw(success, amt);
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
    }

    private boolean canWithdraw (int success, int amt) {
        boolean result = false;
        if(success == -1) {
            System.out.println(
                    "Error: Insufficient Funds"
            );
        } else {
            System.out.println(
                    "Successfully withdrew $" + amt +
                            "\nNew balance: $" +  this.bankInfo.get(
                            this.activeAccount
                    ).getAccountBalance() + "\n"
            );
            result = true;
        }
        return result;
    }

    private void deposit() {
        try {
            System.out.println(
                    "\n-----Deposit-----\nEnter you deposit amount before inserting cash:"
            );
            int amt = scanIn.nextInt();
            this.bankInfo.get(this.activeAccount).incrementBalance(amt);
            System.out.println(
                    "Successfully deposited $" + amt +
                    "\nNew balance: $" +
                    this.bankInfo.get(this.activeAccount).getAccountBalance()
                    + "\n"
            );
            this.machineBalance += amt;
        } catch (IllegalArgumentException err) {
            System.out.println("Error: Please enter an integer");
        }
    }

    private void viewStatement () {

    }

    private void adminMainMenu() {
        System.out.println(
                "\n====================\n1: Check Machine Balance\n2: " +
                "Machine Withdraw\n3: Machine Deposit\n4: View Statement\n0:"
                + " Exit\n====================\n"
        );
        try {
            int option = this.scanIn.nextInt();
            boolean condition = false;
            while (!condition) {
                System.out.println(
                        "Please enter an option using the pin pad: "
                );
                if (option == 1) {
                    checkMachineBalance();
                } else if (option == 2) {
                    machineWithdraw();
                } else if (option == 3) {
                    machineDeposit();
                } else if (option == 4) {
                    viewStatement(); //need to make an admin version
                } else if (option == 0) {
                    mainMenu();
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
                this.machineBalance + "\n"
        );
    }

    private void machineWithdraw () {
        boolean condition = false;
        while(!condition) {
            try {
                System.out.println(
                        "\n-----Machine Withdraw-----\nEnter withdraw amount:"
                );
                int amt = scanIn.nextInt();
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
                            + "\n"
            );
        } catch (IllegalArgumentException err) {
            System.out.println("Error: Please enter an integer");
        }
    }

    private void incrementMachineBalance (int amt) { this.machineBalance += amt; }

    private void decrementMachineBalance (int amt) { this.machineBalance -= amt; }
}
