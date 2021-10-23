package task3;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main_task3 {
    private static final String RESET_COLOR = "\u001b[0m";
    private static final int TARGET_CLIENTS = 5;
    private static final int STAFF_SIZE = 2;
    private static final int THREADS = 4;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(RESET_COLOR + "\n\tЗадача 3***. Ресторан\n");

        final ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        final Restaurant restaurant = new Restaurant(STAFF_SIZE, TARGET_CLIENTS);

        for (int i = 0; i < TARGET_CLIENTS; i++) {
            final Client client = new Client(restaurant);
            pool.submit(() -> restaurant.newClient(client));
        }
        pool.shutdown();
    }
}