package task2;

public class Customer {
    private final String COLOR = "\u001b[32m";
    private static int counter;
    private final int ID;
    private int garageSize = 0;
    private final Manufacturer targetManufacturer;

    public Customer(Manufacturer manufacturer) {
        ID = ++counter;
        targetManufacturer = manufacturer;
        System.out.println(COLOR + "В автосалоне новый покупатель " + ID);
    }

    public int getGarageSize() { return garageSize; }

    public int getID() { return ID; }

    public void buy(int request) {
        System.out.println(COLOR + "Покупатель " + ID + " хочет купить " + request + " авто");
        if (targetManufacturer.getStock() < request) {
            System.out.println(COLOR + "Покупатель " + ID + " ожидает поставки");
        }
        targetManufacturer.sell(request);
        garageSize += request;
        System.out.println(COLOR + "Покупатель " + ID + " приобрёл " + request + " авто (в гараже " + garageSize + ")");
    }

    public void buy() {
        buy(1);
    }
}