package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Position;

import java.util.List;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.generators.RandomGenerator.randInt;

public class Area {

    private final List<List<Cell>> cells;
    private final int height;
    private final int width;

    public Area(List<List<Cell>> cells) {
        this.cells = cells;
        height = cells.size();
        width = cells.get(0).size();
    }

    public Area subArea(int x, int y, int width, int height) {
        return new Area(cells.subList(y, y + height).stream()
                .map(list -> list.subList(x, x + width))
                .collect(Collectors.toList()));
    }

    public Cell getCell(int x, int y) {
        return cells.get(y).get(x);
    }

    public Cell getCell(Position position) {
        return getCell(position.x(), position.y());
    }

    public Position getCellPosition(int x, int y) {
        return getCell(x, y).getPosition();
    }

    public Cell getRandCell() {
        return getCell(randInt(0, width - 1), randInt(0, height - 1));
    }

    public Position getRandCellPosition() {
        return getRandCell().getPosition();
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public Position getPosition() {
        return cells.get(0).get(0).getPosition();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
