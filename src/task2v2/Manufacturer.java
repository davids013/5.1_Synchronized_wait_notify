package task2v2;

public class Manufacturer {
    private final String COLOR = "\u001b[35m";
    private final int SUPPLY_SIZE = 1;
    private final int SUPPLY_DELAY = 800;
    private boolean isEnough = false;
    private Dealer dealer;

    public Manufacturer() {
        regularSupply();
    }

    public Manufacturer setDealer(Dealer dealer) {
        this.dealer = dealer;
        return this;
    }

    public void setEnough(boolean enough) { isEnough = enough; }

    private void regularSupply() {
        new Thread(() -> {
            while (!isEnough) {
                if (dealer == null) continue;
                supply();
                try {
                    Thread.sleep(SUPPLY_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(COLOR + "Конвейер производителя остановлен");
        }).start();
    }


    private void supply(int numOfGoods) {
        System.out.println(COLOR + "Производитель поставляет " + numOfGoods + " авто");
        dealer.newSupply(numOfGoods);
    }

    public void supply() {
        supply(SUPPLY_SIZE);
    }
}