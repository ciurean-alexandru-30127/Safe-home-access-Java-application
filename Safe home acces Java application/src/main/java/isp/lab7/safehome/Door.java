package isp.lab7.safehome;

import static isp.lab7.safehome.DoorStatus.CLOSE;

public class Door {
    DoorStatus status = CLOSE;

    public DoorStatus getStatus() {
        return status;
    }

    public void setStatus(DoorStatus status) {
        this.status = status;
    }

    public void lockDoor() {
        this.status = DoorStatus.CLOSE;
    }

    public void unlockDoor() {
        this.status = DoorStatus.OPEN;
    }
}
