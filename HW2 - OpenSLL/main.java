import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        user.setEncrpytionType(getHashValue(user.hash));
        user.saltValue = getSaltValue(user.hash);


    }

    public static String getSaltValue(String hashValue) {
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
                return saltValue;
            }
        }

        //Define ending point
        int endPoint = curPoint - 1;

        //Find salt value and set the return
        saltValue = hashValue.substring(startPoint, endPoint);

        return saltValue;
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

    public static String getHashValue(String passHash) {
        //Get hashing type
        String shadowHash = passHash.substring(0, 3);

        //Check to make sure its right lenght
        if (!shadowHash.endsWith("$")) {
            shadowHash = shadowHash.substring(0, 2);
        }

        //Return set hash type
        return shadowHash;
    }
}

