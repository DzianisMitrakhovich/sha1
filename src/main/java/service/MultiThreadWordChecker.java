package service;

import org.apache.commons.codec.digest.DigestUtils;

public class MultiThreadWordChecker implements Runnable {
    private String word;
    private String hash;
    private static boolean found;
    private volatile String result;


    MultiThreadWordChecker(String word, String hash) {
        this.word = word;
        this.hash = hash;
    }

    public MultiThreadWordChecker() {
    }

    public void run() {
        if (DigestUtils.sha1Hex(word).equals(hash)) {
            System.out.println("Your password is: " + word);
            setResult(word);
            found = true;
        }
    }

    public static boolean isFound() {
        return found;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
