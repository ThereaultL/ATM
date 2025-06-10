public class Account {
    private final int accountNumber;
    private int accountPin;
    private int accountBalance;
    private final boolean isAdmin;

    public Account (int num, int pin) {
        this.accountNumber = num;
        this.accountPin = pin;
        this.accountBalance = 0;
        this.isAdmin = false;
    }

    public Account (int num, int pin, boolean isAdmin) {
        this.accountNumber = num;
        this.accountPin = pin;
        this.accountBalance = 0;
        this.isAdmin = isAdmin;
    }

    public int getAccountNumber() { return this.accountNumber; }

    public void setAccountPin(int pin) {
        if(Integer.toString(pin).length() == 4) {
            this.accountPin = pin;
        } else {
            System.out.println("Personal pin must be four digits!");
        }
    }

    public int getAccountPin() { return this.accountPin; }

    public void setAccountBalance(int amt) { this.accountBalance = amt; }

    public int getAccountBalance() { return this.accountBalance; }

    public boolean getIsAdmin() { return this.isAdmin; }

    public void incrementBalance(int amt) { this.accountBalance += amt; }

    public int decrementBalance(int amt) {
        int result = -1;
        if(!(this.accountBalance - amt < 0)) {
            this.accountBalance -= amt;
            result = 1;
        }
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(
                "\n-----Account Balance-----\n$" + this.accountBalance
        );
        return result.toString();
    }
}
