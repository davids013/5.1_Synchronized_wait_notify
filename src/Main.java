import task1.Main_task1;
import task2.Main_task2;
import task3.Main_task3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int INTERTASK_DELAY = 1_000;
        System.out.println("""
                                
                \tМодуль 5. Многопоточное и функциональное программирование
                \tЛекция 2. Работа с синхронизацией""");

        Main_task1.main(null);
        while (!Main_task1.getPool().isTerminated());

        Thread.sleep(INTERTASK_DELAY);
        Main_task2.main(null);
        while (!Main_task2.getPool().isTerminated());

        Thread.sleep(INTERTASK_DELAY);
        Main_task3.main(null);
    }
}
