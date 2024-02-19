public class UserAccount {
    public String username;
    public String hash;
    public int lastChanged;
    public int minPasswordDate;
    public int maxPasswordDate;
    public int warn;

    public String encrpytionType;
    public String saltValue;
    public String password;

    // Constructor
    public UserAccount(String username, String hash, int lastChanged,
                       int minPasswordDate, int maxPasswordDate, int warn) {
        this.username = username;
        this.hash = hash;
        this.lastChanged = lastChanged;
        this.minPasswordDate = minPasswordDate;
        this.maxPasswordDate = maxPasswordDate;
        this.warn = warn;
        this.password = "this isnt working";
    }

    public UserAccount(){}

    public void setEncrpytionType(String hashValue) {
        //Determine hash type
        this.encrpytionType = switch (hashValue) {
            case "$1$" -> "MD5";
            case "$2a$", "$2y$" -> "Blowfish";
            case "$5$" -> "SHA-256";
            case "$6$" -> "SHA-512";
            case "$y$" -> "yescrypt";
            default -> "ERROR";
        };

        this.hash = hash.substring(hashValue.length());
    }

    public void setSaltValue(String saltValue) {
        this.saltValue = saltValue;
        this.hash = hash.substring(saltValue.length() + 1);
    }
}
