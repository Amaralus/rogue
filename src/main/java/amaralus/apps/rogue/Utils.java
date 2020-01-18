package amaralus.apps.rogue;

import amaralus.apps.rogue.entities.world.Cell;

import java.util.List;
import java.util.stream.Collectors;

public final class Utils {

    private Utils() {}

    public static List<List<Cell>> subField(List<List<Cell>> field, int x, int y, int width, int height) {
        return field.subList(y, y + height).stream()
                .map(list -> list.subList(x, x + width))
                .collect(Collectors.toList());
    }
}
