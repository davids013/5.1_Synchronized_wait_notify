public class Manufacturer {
    private final String COLOR = "\u001b[35m";
    private final int REQUEST_DELAY = 200;
    private final int SELL_DELAY = 500;
    private final int SUPPLY_DELAY = 3000;
    private final int SUPPLY_SIZE = 2;
    private int stock;

    public Manufacturer() { stock = 0; }

    public Manufacturer(int stock) { this.stock = stock; }

    public int getStock() { return stock; }

    public synchronized boolean sell(int request) {
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
            System.out.println(COLOR + "Производитель продал " + request + " авто (осталось " + stock + ")");
            return true;
        } else {
            while (stock < request) {
                try {
                    System.out.println(COLOR + "У производителя недостаточно авто в наличии (" + stock + ")");
                    new Thread(() -> supply(SUPPLY_SIZE)).start();
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    public synchronized void supply(int numOfGoods) {
        System.out.println(COLOR + "Запущено производство " + numOfGoods + " авто");
        try {
            Thread.sleep(SUPPLY_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stock += numOfGoods;
        System.out.println(COLOR + "Производитель изготовил " + numOfGoods + " авто");
        notify();
    }

    public synchronized void supply() {
        System.out.println(COLOR + "Запущено производство " + SUPPLY_SIZE + " авто");
        try {
            Thread.sleep(SUPPLY_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stock += SUPPLY_SIZE;
        System.out.println(COLOR + "Производитель изготовил " + SUPPLY_SIZE + " авто");
    }
}
