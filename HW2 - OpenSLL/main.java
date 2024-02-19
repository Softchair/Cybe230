import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class main {

    public static UserAccount user;

    public static void main(String[] args) throws IOException {
        //Define files
        File passwordList = new File ("HW2 - OpenSLL/100k-most-used-passwords-NIST.txt");
        File shadowFile = new File ("HW2 - OpenSLL/shadow.txt");

        //Get shadow hash out of file
        Scanner scanner = new Scanner(shadowFile);
        String shadowHash = scanner.next();
        scanner.close();

        //Get user object
        user = getUserObject(shadowHash);

        //Determine hashing type
        getHashValue(user.hash);
        //Get the salting value
        getSaltValue(user.hash);
        //Cracking password
        crackPassword(user.hash, passwordList);



        //Print out finals stats
        printStats(user);
    }

    public static void printStats(UserAccount user) {
        System.out.println("Hash value: " + user.hash);
        System.out.println("Salt value: " + user.saltValue);
        System.out.println("Password: " + user.password);
    }

    public static void crackPassword(String passHash, File passwordList) throws FileNotFoundException {
        Scanner scan = new Scanner(passwordList);

        //Value to store the current hash
        String curHash = "";
        //Value to store current password
        //String curPassword = scan.nextLine();
        String curPassword = "password";
        while(scan.hasNext()) {

            String saltedPassword = user.saltValue + curPassword;

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");

                byte[] hash = md.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

                StringBuilder sb = new StringBuilder();
                for (byte b : hash) {
                    sb.append(String.format("%02x", b));
                }

                curHash = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException();
            }

            if (curHash.equals(user.hash)) {
                user.password = curPassword;
                return;
            }

            curPassword = scan.nextLine();
        }

        //user.password = "WHY";
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len /  2];
        for (int i =  0; i < len; i +=  2) {
            data[i /  2] = (byte) ((Character.digit(s.charAt(i),  16) <<  4)
                    + Character.digit(s.charAt(i+1),  16));
        }
        return data;
    }

    public static void getSaltValue(String hashValue) {
        //Define return value
        String saltValue = "Error";
        //Starting point of salt
        int startPoint = 0;

        //Defining points
        int curPoint = startPoint + 1;
        char curChar = hashValue.charAt(4);
        //Iterate through string till find end of has
        while (curChar != '$') {
            curChar = hashValue.charAt(curPoint);
            curPoint++;

            //Return if error
            if (curPoint - 1 >= hashValue.length()) {
                break;
            }
        }

        //Define ending point
        int endPoint = curPoint - 1;

        //Find salt value and set the return
        saltValue = hashValue.substring(startPoint, endPoint);

        user.setSaltValue(saltValue);
    }

    public static UserAccount getUserObject(String shadowString) {
        //Setup return value
        UserAccount user = new UserAccount();

        //Split string into parts
        String[] shadowHashPart = shadowString.split(":");

        //Set user details according to string
        user.username = shadowHashPart[0];
        user.hash = shadowHashPart[1];
        user.lastChanged = Integer.parseInt(shadowHashPart[2]);
        user.minPasswordDate = Integer.parseInt(shadowHashPart[3]);
        user.maxPasswordDate = Integer.parseInt(shadowHashPart[4]);
        user.warn = Integer.parseInt(shadowHashPart[5]);


        return user;
    }

    public static void getHashValue(String passHash) {
        //Get hashing type
        String shadowHash = passHash.substring(0, 3);

        //Check to make sure its right lenght
        if (!shadowHash.endsWith("$")) {
            shadowHash = shadowHash.substring(0, 2);
        }

        //Return set hash type
        user.setEncrpytionType(shadowHash);
    }
}