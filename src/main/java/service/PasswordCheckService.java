package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordCheckService {
    private String guess;
    private String hash;
    private volatile String passwordCracked;
    private ExecutorService executorService = Executors.newFixedThreadPool(1); // create a pool of N threads
    private List<String> combinations = new ArrayList<String>(); // set count with all possible combinations

    public PasswordCheckService() {
    }

    private PasswordCheckService(String guess, String hash) {
        this.guess = guess;
        this.hash = hash;
    }

    public String findPassword(int maxNumberOfCharacters, char[] guessingRange, String hash) {
        for (int i = 0; i <= maxNumberOfCharacters; i++) {
            generatePassword("", i, guessingRange);
        }
        // join threads here
        executorService.shutdownNow();
        return getPasswordCracked();
    }

    private void generatePassword(String guess, int numberOfInputCharacters, char[] guessingRange) {
        combinations.add(guess);
        executorService.submit(new MultiThreadWordChecker(guess, hash, this));
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
