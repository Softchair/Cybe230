import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class main {


    public static void main(String[] args) throws IOException {
        //Define files
        File passwordList = new File ("HW2 - OpenSLL/100k-most-used-passwords-NIST.txt");
        File shadowFile = new File ("HW2 - OpenSLL/shadow.txt");

        //Get shadow hash out of file
        Scanner scanner = new Scanner(shadowFile);
        String shadowHash = scanner.next();
        scanner.close();

        UserAccount user = getUserObject(shadowHash);

        System.out.println(user.hash);

    }

    public static UserAccount getUserObject(String shadowString) {
        //Setup return value
        UserAccount user = new UserAccount();

        String[] shadowHashPart = shadowString.split(":");

        user.username = shadowHashPart[0];
        user.hash = shadowHashPart[1];
        user.lastChanged = Integer.parseInt(shadowHashPart[2]);
        user.minPasswordDate = Integer.parseInt(shadowHashPart[3]);
        user.maxPasswordDate = Integer.parseInt(shadowHashPart[4]);
        user.warn = Integer.parseInt(shadowHashPart[5]);


        return user;
    }
}

