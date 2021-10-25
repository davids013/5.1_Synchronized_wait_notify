package task3;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private final Restaurant restaurant;
    private static int counter;
    private final int ID;
    private final int EAT_DELAY = 2_000;
    public static final String COLOR = "\u001b[32m";
    private boolean isOrdered;
    private boolean isReadyToOrder;
    private Waiter waiter;
    private final Lock locker = new ReentrantLock();

    public Client(Restaurant restaurant) {
        ID = ++counter;
        this.restaurant = restaurant;
    }

    public int getID() {
        return ID;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    public boolean isReadyToOrder() {
        return isReadyToOrder;
    }

    public void setReadyToOrder(boolean readyToOrder) {
        isReadyToOrder = readyToOrder;
    }

    public void order() {
        System.out.println(COLOR + this + " готов сделать заказ");
        isReadyToOrder = true;
        waiter = restaurant.callWaiter(this);
    }

    public void eat() {
        isOrdered = true;
        isReadyToOrder = false;
        System.out.println(COLOR + this + " принялся за пищу");
        try {
            Thread.sleep(EAT_DELAY + (int) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        restaurant.clientGone(this);
    }

    @Override
    public String toString() {
        return "Посетитель " + ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }

    public Lock getLock() {
        return locker;
    }
}