package task2;

public class Customer {
    private final String COLOR = "\u001b[32m";
    private static int counter;
    private final int id;
    private int garageSize = 0;
    private final Manufacturer targetManufacturer;

    public Customer(Manufacturer manufacturer) {
        counter += 1;
        id = counter;
        targetManufacturer = manufacturer;
        System.out.println(COLOR + "В автосалоне новый покупатель " + id);
    }

    public int getGarageSize() { return garageSize; }

    public int getId() { return id; }

    public boolean buy(int request) {
        System.out.println(COLOR + "Покупатель " + id + " хочет купить " + request + " авто");
        if (targetManufacturer.getStock() < request) {
            System.out.println(COLOR + "Покупатель " + id + " ожидает поставки");
        }
        targetManufacturer.sell(request);
        garageSize += request;
        System.out.println(COLOR + "Покупатель " + id + " приобрёл " + request + " авто (в гараже " + garageSize + ")");
        return true;
    }

    public boolean buy() {
        return buy(1);
    }
}
