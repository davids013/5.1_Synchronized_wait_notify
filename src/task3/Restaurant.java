package task3;

import java.util.Collection;
import java.util.HashSet;

public class Restaurant {
    private final int TARGET_CLIENTS;
    private final int STAFF_SIZE;
    private final int CHOOSE_TIME = 500;
    private final Collection<Waiter> waiters;
    private final Collection<Client> clients;
    protected static final String COLOR = "\u001b[36m";
    private volatile int served;

    public Restaurant(int staffSize, int targetClients) {
        STAFF_SIZE = staffSize;
        TARGET_CLIENTS = targetClients;
        waiters = new HashSet<>();
        for (int i = 0; i < STAFF_SIZE; i++) waiters.add(new Waiter());
        clients = new HashSet<>();
    }

    public void newClient(Client client) {
        if (clients.size() >= TARGET_CLIENTS) {
            System.out.println(COLOR +
                    client + " не может войти. Ресторан не обслуживает более " + TARGET_CLIENTS + " клиентов");
            return;
        }
        System.out.println(Client.COLOR + client + " заходит в ресторан");
        clients.add(client);
        try {
            Thread.sleep(CHOOSE_TIME + (int) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.order();
    }

    public void clientGone(Client client) {
        ++served;
        clients.remove(client);
        System.out.println(COLOR + client + " расплатился и ушёл. Обслужено " + served + " посетителей");
        if (served >= TARGET_CLIENTS && (clients.size() == 0 || "[]".equals(clients.toString())))
            System.out.println(COLOR + "Ресторан закрывается. Персонал собирается домой");
    }

    public Waiter callWaiter(Client client) {
        System.out.println(Client.COLOR + client + " вызвал официанта");
        if (served >= TARGET_CLIENTS) {
            System.out.println(COLOR + "Ресторан закрыт");
            return null;
        }
        while (clients.size() > 0) {
            if (!client.isReadyToOrder() || client.isOrdered()) break;
            for (Waiter waiter : waiters) {
                if (waiter.isFree()) {
                    if (waiter.getLock().tryLock()) {
                        waiter.setFree(false);
                        System.out.println(COLOR + client + " обслужит " + waiter);
                        new Thread(() -> waiter.takeOrder(client)).start();
                        waiter.getLock().unlock();
                        return waiter;
                    }
                }
            }
        }
        return null;
    }
}