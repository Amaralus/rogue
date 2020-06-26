package amaralus.apps.rogue.generators;

import java.util.concurrent.atomic.AtomicLong;

public final class UniqueIdGenerator {

    private static final AtomicLong atomicLong = new AtomicLong();

    private UniqueIdGenerator() {}

    public static long uniqueId() {
        return atomicLong.incrementAndGet();
    }
}
