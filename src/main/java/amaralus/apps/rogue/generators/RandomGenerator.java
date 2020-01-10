package amaralus.apps.rogue.generators;

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

    public static boolean randBoolean() {
        return getInstance().random.nextBoolean();
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
