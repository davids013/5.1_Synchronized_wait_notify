package task1;

public class Manufacturer {
    private final String COLOR = "\u001b[35m";
    private final int REQUEST_DELAY = 200;
    private final int SELL_DELAY = 300;
    private final int SUPPLY_SIZE = 1;
    private int targetSales;
    private int supplyDelay = 500;
    private int stock;
    private int sold;

    public Manufacturer(int stock) {
        this.stock = stock;
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
                        supply();
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

    public synchronized void sell(int request) {
        System.out.println(COLOR + "У производителя запросили " + request + " авто");
        try {
            Thread.sleep(REQUEST_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (stock >= request) {
            try {
                Thread.sleep(SELL_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock -= request;
            sold += request;
            System.out.println(COLOR + "Производитель продал " + request + " авто (осталось " + stock + ")");
        } else {
            while (stock < request) {
                try {
                    System.out.println(COLOR + "У производителя недостаточно авто (в наличии " + stock + ")");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stock -= request;
            sold += request;
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
