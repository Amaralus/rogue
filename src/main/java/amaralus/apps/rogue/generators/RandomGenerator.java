package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.Room;

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

    public static int randInt(int from, int to) {
        return getInstance().random.nextInt((to - from) + 1) + from;
    }

    public static int excRandInt(int from, int to) {
        from++;
        return getInstance().random.nextInt(to - from) + from;
    }

    public static int randInt(int to) {
        return getInstance().random.nextInt(to + 1);
    }

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

    public static Position randPositionFromRoom(Room room) {
        int x = excRandInt(0, room.getWidth() - 1);
        int y = excRandInt(0, room.getHeight() - 1);

        return room.getRoomCells().get(y).get(x).getPosition();
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
