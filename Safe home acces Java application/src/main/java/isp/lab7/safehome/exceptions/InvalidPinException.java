package isp.lab7.safehome.exceptions;

public class InvalidPinException extends Exception {
    public InvalidPinException(String c) {
        System.out.println(c);
    }

    public InvalidPinException() {
    }
}
