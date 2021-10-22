package task3;

public class Waiter {
    private static final String COLOR = "\u001b[32m";
    private static final int TAKE_ORDER_TIME = 200;
    private static int counter;
    private final int ID;

    public Waiter() {
        counter++;
        ID = counter;
    }

    public void order() {
        System.out.println(COLOR + "Официант " + ID + " принимает заказ");
    }

}