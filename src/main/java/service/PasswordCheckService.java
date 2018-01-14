package service;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PasswordCheckService {
    private String hash;
    private volatile String passwordCracked;
    //private ExecutorService executorService = Executors.newFixedThreadPool(1); // create a pool of N threads

    public PasswordCheckService() {
    }

    public PasswordCheckService(String hash) {
        this.hash = hash;
    }

    public String findPassword(int maxNumberOfCharacters, final char[] guessingRange) throws InterruptedException {
        ExecutorService executorServiceLocal = Executors.newFixedThreadPool(1);

        for (int i = 0; i <= maxNumberOfCharacters; i++) {
            final int currentNumberOfCombinations = i;

            executorServiceLocal.submit(new Runnable() {

                public void run() {
                    generatePassword("", currentNumberOfCombinations, guessingRange);
                }
            });
        }
        executorServiceLocal.shutdown();
        executorServiceLocal.awaitTermination(Integer.MAX_VALUE, TimeUnit.MINUTES);

        return getPasswordCracked();
    }

    private void generatePassword(String guess, int numberOfInputCharacters, char[] guessingRange) {
        if (numberOfInputCharacters > 0) {
            for (char character : guessingRange) {
                if (!isPasswordFound()) {
                    generatePassword(
                            guess + character,
                            numberOfInputCharacters - 1,
                            guessingRange);
                } else {
                    return;
                }
            }
        } else {
            System.out.println(guess);
            if (DigestUtils.sha1Hex(guess).equals(hash)) {
                this.setPasswordCracked(guess);
            }
        }

    }

    private boolean isPasswordFound() {
        return passwordCracked != null;
    }

    public String getPasswordCracked () {
        return passwordCracked;
    }

    public void setPasswordCracked (String passwordCracked){
        this.passwordCracked = passwordCracked;
    }

}
