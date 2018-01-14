package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordCheckService {
    private String guess;
    private String hash;
    private volatile String passwordCracked;
    private ExecutorService executorService = Executors.newFixedThreadPool(10); // create a pool of N threads

    public PasswordCheckService() {
    }

    public PasswordCheckService(String hash) {
        this.hash = hash;
    }

    public String findPassword(int maxNumberOfCharacters, char[] guessingRange) {
        for (int i = 0; i <= maxNumberOfCharacters; i++) {
            generatePassword("", i, guessingRange);
        }
        // join threads here
        executorService.shutdownNow();
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
            executorService.submit(new MultiThreadWordChecker(guess, hash, this));
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
