package isp.lab7.safehome.exceptions;

public class TenantAlreadyExistsException extends Exception {
    public TenantAlreadyExistsException(String s){
        System.out.println(s);
    }
}
