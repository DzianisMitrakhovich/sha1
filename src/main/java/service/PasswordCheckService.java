package service;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordCheckService {
    private String guess;
    private String hash;
    private String passwordCracked;
    private ExecutorService executorService = Executors.newFixedThreadPool(10); // create a pool of N threads

    public PasswordCheckService() {
    }

    private PasswordCheckService(String guess, String hash) {
        this.guess = guess;
        this.hash = hash;
    }

    public String findPassword(int maxNumberOfCharacters, char[] guessingRange, String hash) {
        for (int i = 0; i <= maxNumberOfCharacters; i++) {
            if (generatePassword("", i, guessingRange, hash)) {
                break;
            }
        }
        return getPasswordCracked();
    }

    private boolean generatePassword(String guess, int numberOfInputCharacters, char[] guessingRange, String hash) {
        System.out.println(guess); // generate a word and pass it for a check
        if (numberOfInputCharacters == 0) {
            if (checkIfPasswordIsFound(guess, hash)) {
                setPasswordCracked(guess); // assign the result to a variable
                executorService.shutdownNow(); //shutdown the thread pool
                return true;
            }
            else {
                return false;
            }
        }
        for (char character : guessingRange) {
            if (generatePassword(
                    guess + character,
                    numberOfInputCharacters - 1,
                    guessingRange,
                    hash)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfPasswordIsFound (String guess, String hash) {
        executorService.submit(new MultiThreadWordChecker(guess, hash)); // pass a word for check to the thread pool
        return MultiThreadWordChecker.isFound();
    }

    public String getPasswordCracked () {
        return passwordCracked;
    }

    public void setPasswordCracked (String passwordCracked){
        this.passwordCracked = passwordCracked;
    }

}
