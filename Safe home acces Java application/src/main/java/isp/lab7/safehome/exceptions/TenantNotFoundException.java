package isp.lab7.safehome.exceptions;

public class TenantNotFoundException extends Exception {
    public TenantNotFoundException(String a) {
        System.out.println(a);
    }
}
