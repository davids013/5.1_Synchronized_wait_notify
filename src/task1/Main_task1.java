package task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main_task1 {
    private static final int    NUM_OF_CUSTOMERS = 5;
    public static final int     TARGET_SALES = 10;
    private static final int    SUPPLY_DELAY = 800;
    private static final int    THREADS = 4;
    private static final String COLOR = "\u001b[0m";
    private static final ExecutorService pool = Executors.newFixedThreadPool(THREADS);

    public static void main(String[] args) {
        System.out.println(COLOR + "\n\tЗадача 1. Автосалон (стандартные средства синхронизации)");
        final Manufacturer m =
                new Manufacturer(0)
                        .setSupplyDelay(SUPPLY_DELAY)
                        .setTargetSales(TARGET_SALES);
        final List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < NUM_OF_CUSTOMERS; i++) {
            customers.add(new Customer(m));
        }
        for (int i = 0; i < TARGET_SALES; i++) {
            Customer nc = customers.get((int) (Math.random() * NUM_OF_CUSTOMERS));
            pool.submit(new Thread(null, nc::buy), "Покупатель " + nc.getID());
        }
        pool.shutdown();
    }

    public static ExecutorService getPool() { return pool; }
}
