package amaralus.apps.rogue.utils;

import java.util.function.Predicate;

public final class Utils {

    private Utils() {
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
}
