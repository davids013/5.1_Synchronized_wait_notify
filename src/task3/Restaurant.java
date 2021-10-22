package task3;

import java.util.List;
import java.util.ArrayList;

public class Restaurant {
    private final int TARGET_CLIENTS;
    private final int STAFF_SIZE;
    private final List<Waiter> waiters;

    public Restaurant(int staffSize, int targetClients) {
        STAFF_SIZE = staffSize;
        TARGET_CLIENTS = targetClients;
        waiters = new ArrayList<>();
    }
}