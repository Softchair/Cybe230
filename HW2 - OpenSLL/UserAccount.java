public class UserAccount {
    public String username;
    public String hash;
    public int lastChanged;
    public int minPasswordDate;
    public int maxPasswordDate;
    public int warn;

    // Constructor
    public UserAccount(String username, String hash, int lastChanged,
                       int minPasswordDate, int maxPasswordDate, int warn) {
        this.username = username;
        this.hash = hash;
        this.lastChanged = lastChanged;
        this.minPasswordDate = minPasswordDate;
        this.maxPasswordDate = maxPasswordDate;
        this.warn = warn;
    }

    public UserAccount(){}
}
