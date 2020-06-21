package amaralus.apps.rogue.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomGenerator {

    private static RandomGenerator instance;

    private Random random;
    private long seed;

    private RandomGenerator() {
        seed = new Random().nextLong();
        random = new Random(seed);
    }

    public static RandomGenerator getInstance() {
        if (instance == null) {
            instance = new RandomGenerator();
        }
        return instance;
    }

    /**
     * Случайное число от нижнего указанного до вернего указанного включая эти числа.
     */
    public static int randInt(int from, int to) {
        return getInstance().random.nextInt((to - from) + 1) + from;
    }

    /**
     * Случайное число от нижнего указанного до вернего указанног исключая эти самые числа.
     */
    public static int excRandInt(int from, int to) {
        from++;
        return getInstance().random.nextInt(to - from) + from;
    }

    /**
     * Случайное число от 0 до указанного включая его само.
     */
    public static int randInt(int to) {
        return getInstance().random.nextInt(to + 1);
    }

    /**
     * Случайное число до указанного исключая его само
     */
    public static int excRandInt(int to) {
        return getInstance().random.nextInt(to);
    }

    public static boolean randBoolean() {
        return getInstance().random.nextBoolean();
    }

    public static <E> E randElement(List<E> list) {
        return list.get(getInstance().random.nextInt(list.size()));
    }

    public static  <E> List<E> randUniqueElements(List<E> elements, int count) {
        List<E> elementList = new ArrayList<>(elements);
        List<E> randomElements = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            int index = excRandInt(elementList.size());
            randomElements.add(elementList.remove(index));
        }

        return randomElements;
    }

    public static  <E> List<E> randUniqueElementsPercent(List<E> elements, int percent) {
        if (percent > 100) percent = 100;

        return randUniqueElements(elements, percentOfNumber(elements.size(), percent));
    }

    public static int percentOfNumber(int number, int percent) {
        return (int) (number * ((double) percent / 100.0d));
    }

    public static boolean randChancePercent(int percent) {
        if (percent > 100) percent = 100;

        return randInt(100) <= percent;
    }

    public static boolean randChanceFraction(int severalCount, int ofCount) {
        return randInt(ofCount) < severalCount;
    }

    public static boolean randDice6() {
        return randChanceFraction(1, 6);
    }

    public static boolean randDice3() {
        return randChanceFraction(1, 3);
    }

    public static boolean randDice20() {
        return randChanceFraction(1, 20);
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
