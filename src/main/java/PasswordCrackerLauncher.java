import org.apache.commons.codec.digest.DigestUtils;
import service.MultiThreadWordChecker;
import service.PasswordCheckService;

public class PasswordCrackerLauncher {
    private final static char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    public static void main(String[] args) {
        long timeBefore = System.currentTimeMillis();

        String p = "hiG";
        String hash = DigestUtils.sha1Hex(p);

        PasswordCheckService passwordCheckService = new PasswordCheckService();
        String result = passwordCheckService.findPassword(5, chars, hash);

        MultiThreadWordChecker multiThreadWordChecker = new MultiThreadWordChecker();
        System.out.println(multiThreadWordChecker.getResult());

        long timeAfter = System.currentTimeMillis();

        System.out.println("Time taken: " + (timeAfter - timeBefore));
    }
}
