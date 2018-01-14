package service;

import org.apache.commons.codec.digest.DigestUtils;

public class MultiThreadWordChecker implements Runnable {
    private String word;
    private String hash;
    private PasswordCheckService service;


    MultiThreadWordChecker(String word, String hash, PasswordCheckService service) {
        this.word = word;
        this.hash = hash;
        this.service = service;
    }

    public MultiThreadWordChecker() {
    }

    public void run() {
        if (DigestUtils.sha1Hex(word).equals(hash)) {
            System.out.println("Your password is: " + word);
            service.setPasswordCracked(word);
        }
    }
}
