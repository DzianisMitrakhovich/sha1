import org.apache.commons.codec.digest.DigestUtils;
import service.MultiThreadWordChecker;
import service.PasswordCheckService;

public class PasswordCrackerLauncher {
    private final static char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    public static void main(String[] args) {
        long timeBefore = System.currentTimeMillis();

        String p = "hiGs";
        String hash = DigestUtils.sha1Hex(p);

        PasswordCheckService passwordCheckService = new PasswordCheckService(hash);
        String result = passwordCheckService.findPassword(5, chars);

        System.out.println("Your password is: " + result);

        long timeAfter = System.currentTimeMillis();

        System.out.println("Time taken: " + (timeAfter - timeBefore));
    }
}
