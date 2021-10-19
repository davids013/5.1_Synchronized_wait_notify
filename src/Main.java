import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int NUM_OF_CUSTOMERS = 5;

    public static void main(String[] args) {
        System.out.println("""
                                
                \tМодуль 5. Многопоточное и функциональное программирование
                \tЛекция 2. Работа с синхронизацией""");

        final ExecutorService pool = Executors.newFixedThreadPool(2);
        Manufacturer m = new Manufacturer(2);
        List<Customer> customers = new ArrayList<>();
        List<Callable<Customer>> tasks = new ArrayList<>();
        for (int i = 0; i < NUM_OF_CUSTOMERS; i++) {
            Customer nc = new Customer(m);
            customers.add(nc);
            Callable request = nc::buy;
            tasks.add(request);
//            pool.submit(request);
        }
        try {
            pool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}
