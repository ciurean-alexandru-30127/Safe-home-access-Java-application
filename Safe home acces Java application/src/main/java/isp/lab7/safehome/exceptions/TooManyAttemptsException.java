package isp.lab7.safehome.exceptions;

public class TooManyAttemptsException extends Exception {
    public TooManyAttemptsException(String v) {
        System.out.println(v);
    }
}
