public class Account {
    private final String accountNumber;
    private String accountPin;
    private int accountBalance;
    private final boolean isAdmin;
    private String fullName;

    public Account (String num, String pin, String fullName) {
        this.accountNumber = num;
        this.accountPin = pin;
        this.accountBalance = 0;
        this.isAdmin = false;
        this.fullName = fullName;
    }

    public Account (String num, String pin, boolean isAdmin, String fullName) {
        this.accountNumber = num;
        this.accountPin = pin;
        this.accountBalance = 0;
        this.isAdmin = isAdmin;
        this.fullName = fullName;
    }

    public String getAccountNumber() { return this.accountNumber; }

    public void setAccountPin(String pin) {
        String err = "Personal pin must be four digits!";
        try {
            //ensure our pin is an integer
            Integer.parseInt(pin);
            if(pin.length() == 4) {
                this.accountPin = pin;
            } else {
                System.out.println(err);
            }
        } catch (NumberFormatException e) {
            System.out.println(err);
        }
    }

    public String getAccountPin() { return this.accountPin; }

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

    public String getFullName() { return this.fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(
                "\n-----Account Balance-----\n$" + this.accountBalance
        );
        return result.toString();
    }
}
