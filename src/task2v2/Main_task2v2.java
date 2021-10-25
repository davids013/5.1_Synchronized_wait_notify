package task2v2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main_task2v2 {
    private static final int    NUM_OF_CUSTOMERS = 5;
    public static final int     TARGET_SALES = 10;
    private static final int    THREADS = 4;
    private static final String RESET_COLOR = "\u001b[0m";
    private static final ExecutorService pool = Executors.newFixedThreadPool(THREADS);

    public static void main(String[] args) {
        System.out.println(RESET_COLOR + "\n\tЗадача 2*. Продвинутый автосалон (ред.2 с посредником продаж)\n");
        final Manufacturer m = new Manufacturer();
        final Dealer dealer = new Dealer(m).setTargetSales(TARGET_SALES);
        final List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < NUM_OF_CUSTOMERS; i++) {
            customers.add(new Customer(dealer));
        }
        for (int i = 0; i < TARGET_SALES; i++) {
            Customer nc = customers.get((int) (Math.random() * NUM_OF_CUSTOMERS));
            pool.submit(() -> nc.buy());
        }
        pool.shutdown();
        while (!pool.isTerminated());
    }

    public static ExecutorService getPool() { return pool; }
}