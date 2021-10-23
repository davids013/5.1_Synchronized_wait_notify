package task3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private static final String COLOR = "\u001b[32m";
    private static final int TAKE_ORDER_TIME = 200;
    private static final int DELIVERY_TIME = 500;
    private static final int COOK_TIME = 1_500;
    private static int counter;
    private final int ID;
    private boolean isFree;
    private Client client;
    private final Lock locker = new ReentrantLock();

    public Waiter() {
        ++counter;
        ID = counter;
        isFree = true;
        System.out.println(COLOR + "Официант " + ID + " на работе");
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public void takeOrder(Client client) {
        if (!client.isReadyToOrder() || client.isOrdered()) {
            return;
        }
        locker.lock();
        client.getLock().lock();
        isFree = false;
        this.client = client;
        client.setReadyToOrder(false);
        client.setOrdered(true);
        System.out.println(COLOR + "Официант " + ID + " принимает заказ у посетителя " + client.getID());
        try {
            Thread.sleep(TAKE_ORDER_TIME + (int) (Math.random() * 10));
            client.setOrdered(true);
            System.out.println(COLOR + "Повар готовит заказ для посетителя " + client.getID());
            Thread.sleep(COOK_TIME + (int) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bringOrder();
    }

    private void bringOrder() {
        System.out.println(COLOR + "Официант " + ID + " несёт готовое блюдо посетителю " + client.getID());
        try {
            Thread.sleep(DELIVERY_TIME + (int) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
            client.getLock().unlock();
        }
        isFree = true;
        client.eat();
    }

    @Override
    public String toString() {
        return "Официант " + ID;
    }

    @Override
    public int hashCode() {
        return ID;
    }

    public Lock getLock() {
        return locker;
    }
}