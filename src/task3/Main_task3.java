package task3;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main_task3 {
    private static final String RESET_COLOR = "\u001b[0m";

    public static void main(String[] args) {
        System.out.println(RESET_COLOR + "\n\tЗадача 3*. Ресторан\n");

        final ExecutorService pool = Executors.newFixedThreadPool(4);
//        Waiter waiter = new Waiter();
        Chef chef = new Chef();

        for (int i = 0; i < 3; i++) chef.getOrder();

        pool.submit(new Thread(chef::work));
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chef.setShiftEnd(true);
        pool.shutdown();
    }
}