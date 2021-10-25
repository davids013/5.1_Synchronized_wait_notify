package task1;

public class Manufacturer {
    private final String COLOR = "\u001b[35m";
    private final int REQUEST_DELAY = 200;
    private final int SELL_DELAY = 300;
    private final int SUPPLY_SIZE = 1;
    private final int SUPPLY_DELAY = 500;
    private int targetSales;
    private int stock;
    private int sold;

    public Manufacturer() {
        stock = 0;
        targetSales = 1;
        regularSupply();
    }

    public int getStock() { return stock; }

    public Manufacturer setTargetSales(int targetSales) {
        if (targetSales >= 0)
            this.targetSales = targetSales;
        return this;
    }

    private void regularSupply() {
        new Thread(() -> {
            while (sold < targetSales) {
                supply();
                try {
                    Thread.sleep(SUPPLY_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\u001b[36m" + "Продано " + sold);
            }
            System.out.println("\u001b[36m" + "Объём продаж выполнен!!!");
        }).start();
    }

    public synchronized void sell(int request) {
        System.out.println(COLOR + "У производителя запросили " + request + " авто");
        try {
            Thread.sleep(REQUEST_DELAY);
            if (stock >= request) {
                Thread.sleep(SELL_DELAY);
                stock -= request;
                sold += request;
                System.out.println(COLOR + "Производитель продал " + request + " авто (осталось " + stock + ")");
            } else {
                while (stock < request) {
                    System.out.println(COLOR + "У производителя недостаточно авто (в наличии " + stock + ")");
                    wait();
                }
                stock -= request;
                sold += request;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void supply(int numOfGoods) {
        System.out.println(COLOR + "Запущено производство " + numOfGoods + " авто");
        stock += numOfGoods;
        System.out.println(COLOR + "Производитель изготовил " + numOfGoods + " авто (в наличии " + stock + ")");
        notifyAll();
    }

    public void supply() {
        supply(SUPPLY_SIZE);
    }
}