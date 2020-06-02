package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Position;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.generators.RandomGenerator.randInt;

public class Area implements Destroyable {

    private static int idCounter = 0;

    private final int id;
    private final List<List<Cell>> cells;
    private final Position position;
    private final int height;
    private final int width;

    public Area(List<List<Cell>> cells) {
        this.cells = cells;
        id = ++idCounter;
        position = cells.get(0).get(0).getPosition();
        height = cells.size();
        width = cells.get(0).size();
    }

    @Override
    public void destroy() {
        forEachCell(Cell::destroy);
    }

    public void forEachCell(Consumer<? super Cell> action) {
        for (List<Cell> cellList : cells) {
            for (Cell cell : cellList) {
                action.accept(cell);
            }
        }
    }

    public boolean contains(Cell cell) {
        for (List<Cell> cellList : cells)
            if (cellList.contains(cell))
                return true;
        return false;
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

    public int getId() {
        return id;
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public Position getPosition() {
        return position;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return id == area.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
