package task2v2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dealer {
    private final String COLOR = "\u001b[36m";
    private final int REQUEST_DELAY = 200;
    private final int SELL_DELAY = 300;
    private int targetSales;
    private int stock;
    private int sold;
    private final Lock locker;
    private final Condition condition;
    private final Manufacturer manufacturer;

    public Dealer(Manufacturer manufacturer) {
        stock = 0;
        locker = new ReentrantLock(true);
        condition = locker.newCondition();
        targetSales = 1;
        this.manufacturer = manufacturer;
        manufacturer.setDealer(this);
    }

    public void newSupply(int supply) {
        try {
            locker.lock();
            stock += supply;
            condition.signalAll();
        } finally {
            locker.unlock();
        }
    }

    public int getStock() { return stock; }

    public Dealer setTargetSales(int targetSales) {
        if (targetSales >= 0)
            this.targetSales = targetSales;
        return this;
    }

    public void sell(int request) {
        try {
            locker.lock();
            System.out.println(COLOR + "У дилера запросили " + request + " авто");
            Thread.sleep(REQUEST_DELAY);
            if (stock >= request) {
                Thread.sleep(SELL_DELAY);
            } else {
                while (stock < request) {
                    System.out.println(COLOR + "У дилера недостаточно авто (в наличии " + stock + ")");
                    condition.await();
                }
            }
            stock -= request;
            sold += request;
            System.out.println(COLOR + "Дилер продал " + request + " авто (осталось " + stock + ")");
            if (sold >= targetSales) {
                manufacturer.setEnough(true);
                System.out.println(COLOR + "Дилер выполнил план продаж (" + sold + " авто)!!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }
}