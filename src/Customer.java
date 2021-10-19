public class Customer {
    private final String COLOR = "\u001b[32m";
    private static int counter;
    private final int id;
    private final Manufacturer targetManufacturer;

    public Customer(Manufacturer manufacturer) {
        counter += 1;
        id = counter;
        targetManufacturer = manufacturer;
        System.out.println(COLOR + "Пришёл новый покупатель " + id);
    }

    public boolean buy(int request) {
        System.out.println(COLOR + "Покупатель " + id + " хочет купить " + request + " авто");
        if (targetManufacturer.getStock() >= request) {
            System.out.println(COLOR + "Покупатель " + id + " ожидает следующей поставки");
        }
        boolean isBought = targetManufacturer.sell(request);
//        targetManufacturer.supply(request);
        return isBought;
    }

    public boolean buy() {
        System.out.println(COLOR + "Покупатель " + id + " хочет купить авто");
        System.out.println(COLOR + "Покупатель " + id +
                ((targetManufacturer.getStock() >= 1)
                        ? " приобрел авто" : " ожидает следующей поставки"));
        boolean isBought = targetManufacturer.sell(1);
//        targetManufacturer.supply(1);
        return isBought;
    }
}
