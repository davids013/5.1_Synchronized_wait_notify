package task3;

import java.util.ArrayDeque;
import java.util.Queue;

public class Chef {
    private static final String COLOR = "\u001b[33m";
    private static final int COOK_TIME = 2000;
    private static final int SMOKING_TIME = 500;
    private final Queue<Dish> dishes;
    private int dishesCooked;
    private boolean isShiftEnd;

    public Chef() {
        dishes = new ArrayDeque<>();
        isShiftEnd = false;
    }

    private void cook(Dish dish) {
        System.out.println(COLOR + "Повар готовит блюдо");
        try {
            Thread.sleep(COOK_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dishesCooked++;
        System.out.println(COLOR + "Повар приготовил " + dishesCooked + " блюдо");
    }

    public boolean setShiftEnd(boolean isShiftEnd) {
        this.isShiftEnd = isShiftEnd;
        return isShiftEnd;
    }

    public void getOrder() {
        dishes.add(new Dish());
    }

    public void work() {
        while (!isShiftEnd) {
            if (dishes.size() > 0) {
                cook(dishes.poll());
            } else {
                System.out.println(COLOR + "Повар пошёл на перекур");
                try {
                    Thread.sleep(SMOKING_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Dish {}
}