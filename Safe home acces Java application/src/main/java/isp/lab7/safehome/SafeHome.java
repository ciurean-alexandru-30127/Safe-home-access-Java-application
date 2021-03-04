package isp.lab7.safehome;

import isp.lab7.safehome.exceptions.InvalidPinException;
import isp.lab7.safehome.exceptions.TenantAlreadyExistsException;
import isp.lab7.safehome.exceptions.TenantNotFoundException;
import isp.lab7.safehome.exceptions.TooManyAttemptsException;

public class SafeHome {

    public static void main(String[] args) {
        DoorLockController doorLockController = new DoorLockController();

        try {
            doorLockController.addTenant("123564", "George");
            doorLockController.addTenant("3333", "Alex");
            doorLockController.addTenant("6543", "Larisa");
            doorLockController.addTenant("1234", "George");
        } catch (TenantAlreadyExistsException e) {
            System.out.println("Tenant already exists exception ");
        }
        try {
            doorLockController.addTenant("23455", "Mihai");
        } catch (TenantAlreadyExistsException e) {
            System.out.println("Tenant already exists exception ");
        }

        for (AccesLog accessLog : doorLockController.getAccessLogs()) {
            System.out.println(accessLog.toString());
        }

        try {
            doorLockController.enterPin("306");
        } catch (TooManyAttemptsException f) {
            System.out.println("Too many attempts exception");
        } catch (InvalidPinException e) {
            System.out.println("Invalid pin exception");
        }
        try {
            doorLockController.enterPin("1750");
        } catch (TooManyAttemptsException f) {
            System.out.println("Too many attempts exception");
        } catch (InvalidPinException e) {
            System.out.println("Invalid pin exception");
        }
        try {
            doorLockController.enterPin("42432");
        } catch (TooManyAttemptsException f) {
            System.out.println("Too many attempts exception");
        } catch (InvalidPinException e) {
            System.out.println("Invalid pin exception");
        } finally {
            try {
                doorLockController.enterPin(ControllerInterface.MASTER_KEY);
            } catch (TooManyAttemptsException f) {
                System.out.println("Too many attempts exception");
            } catch (InvalidPinException e) {
                System.out.println("Invalid pin exception");
            }
        }

        try {
            doorLockController.removeTenant("Alin");
        } catch (TenantNotFoundException e) {
            System.out.println("Tenant not found exception");
        }
        try {
            doorLockController.removeTenant("George");
        } catch (TenantNotFoundException e) {
            System.out.println("Tenant not found exception");
        }

    }
}


