package task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Manufacturer {
    private final String COLOR = "\u001b[35m";
    private final int REQUEST_DELAY = 200;
    private final int SELL_DELAY = 300;
    private final int SUPPLY_SIZE = 1;
    private int targetSales;
    private int supplyDelay = 500;
    private int stock;
    private int sold;
    private final Lock locker;
    private final Condition condition;

    public Manufacturer() {
        stock = 0;
        locker = new ReentrantLock(true);
        condition = locker.newCondition();
        targetSales = 1;
        regularSupply();
    }

    public int getStock() { return stock; }

    public int getSold() { return sold; }

    public Manufacturer setSupplyDelay(int delay) {
        if (delay >= 0)
            supplyDelay = delay;
        return this;
    }

    public Manufacturer setTargetSales(int targetSales) {
        if (targetSales >= 0)
            this.targetSales = targetSales;
        return this;
    }

    private void regularSupply() {
        Thread t = new Thread(
                null,
                () -> {
                    while (sold < targetSales) {
//                        if (m.getStock() < 1) {
                        supply();
//                        };
                        try {
                            Thread.sleep(supplyDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("\u001b[36m" + "Продано " + sold);
                    }
                    System.out.println("\u001b[36m" + "Объём продаж выполнен!!!");
                },
                "Производитель");
        t.start();
    }

    public void sell(int request) {
        try {
            locker.lock();
            System.out.println(COLOR + "У производителя запросили " + request + " авто");
            Thread.sleep(REQUEST_DELAY);
            if (stock >= request) {
                Thread.sleep(SELL_DELAY);
            } else {
                while (stock < request) {
                    System.out.println(COLOR + "У производителя недостаточно авто (в наличии " + stock + ")");
                    //                    new Thread(() -> supply(2)).start();
                    condition.await();
                }
            }
            stock -= request;
            sold += request;
            System.out.println(COLOR + "Производитель продал " + request + " авто (осталось " + stock + ")");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    private void supply(int numOfGoods) {
        try {
            locker.lock();
            System.out.println(COLOR + "Запущено производство " + numOfGoods + " авто");
            stock += numOfGoods;
            System.out.println(COLOR + "Производитель изготовил " + numOfGoods + " авто (в наличии " + stock + ")");
            condition.signalAll();
        } finally {
            locker.unlock();
        }
    }

    public void supply() {
        supply(SUPPLY_SIZE);
    }
}