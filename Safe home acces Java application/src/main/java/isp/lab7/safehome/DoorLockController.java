package isp.lab7.safehome;

import isp.lab7.safehome.exceptions.InvalidPinException;
import isp.lab7.safehome.exceptions.TenantAlreadyExistsException;
import isp.lab7.safehome.exceptions.TenantNotFoundException;
import isp.lab7.safehome.exceptions.TooManyAttemptsException;

import java.time.LocalDateTime;
import java.util.*;

public class DoorLockController implements ControllerInterface {

    private static final String addTenantOperation = "addTenant";
    private Map<Tenant, AccesKey> validAccess;
    private List<AccesLog> accessLogs;
    private Door door = new Door();
    private int nrOfTries = 0;
    private boolean doorLock = false;

    public DoorLockController() {
        this.validAccess = new HashMap<>();
        this.validAccess.put(new Tenant(MASTER_TENANT_NAME), new AccesKey(MASTER_KEY));
        this.accessLogs = new ArrayList<>();
    }

    public DoorLockController(Map<Tenant, AccesKey> validAccess, List<AccesLog> accessLogs, Door door) {
        this.validAccess = validAccess;
        this.accessLogs = accessLogs;
        this.door = door;
    }

    public Map<Tenant, AccesKey> getValidAccess() {
        return validAccess;
    }

    public void setValidAccess(Map<Tenant, AccesKey> validAccess) {
        this.validAccess = validAccess;
    }

    public List<AccesLog> getAccessLogs() {
        return accessLogs;
    }

    public void setAccessLogs(List<AccesLog> accessLogs) {
        this.accessLogs = accessLogs;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    /**
     * This method should enter, or open the door, or should throw an exception by case
     *
     * @param pin - pin value
     * @return DoorStatis
     * @throws InvalidPinException
     * @throws TooManyAttemptsException
     */

    @Override
    public DoorStatus enterPin(String pin) throws InvalidPinException, TooManyAttemptsException {
        boolean doorStatus = validAccess.containsValue(new AccesKey(pin));
        if (this.door.getStatus() == DoorStatus.CLOSE && doorStatus) {
            this.door.unlockDoor();
            return DoorStatus.OPEN;
        } else if (this.door.getStatus() == DoorStatus.OPEN && doorStatus) {
            this.door.lockDoor();
            return DoorStatus.CLOSE;
        }
        nrOfTries++;
        if (nrOfTries >= 3) {
            throw new TooManyAttemptsException("Too many attempts");
        }
        if (!doorStatus) {
            throw new InvalidPinException();
        }
        return door.getStatus();
    }

    /**
     * This method should add a tenant amd throw an exception if tenant already exists
     *
     * @param pin  - pin to be added in the system
     * @param name - tenant name to be added in the system
     * @throws TenantAlreadyExistsException
     */

    @Override
    public void addTenant(String pin, String name) throws TenantAlreadyExistsException {

        Tenant tenant = new Tenant(name);
        AccesKey accessKey = new AccesKey(pin);
        if (validAccess.containsKey(tenant)) {
            accessLogs.add(new AccesLog(name, LocalDateTime.now(), addTenantOperation, door.getStatus(), "Not successfully"));
            throw new TenantAlreadyExistsException("The tenant exists " + name);
        }

        validAccess.put(tenant, accessKey);
        System.out.println("Tenant " + name + "  added");
        accessLogs.add(new AccesLog(name, LocalDateTime.now(), addTenantOperation, door.getStatus(), "Operation succeed"));
    }

    /**
     * This method should remove a tenant and throw an exceptopn if this tenant is not found
     *
     * @param name - tenant name to be removed
     * @throws TenantNotFoundException
     */

    @Override
    public void removeTenant(String name) throws TenantNotFoundException {
        Tenant tenant = new Tenant(name);
        if (!this.validAccess.containsKey(tenant)) {
            throw new TenantNotFoundException("This tenant is not found");
        } else {
            this.validAccess.remove(tenant);
            System.out.println("Tenant " + name + " removed");
        }
    }

}

