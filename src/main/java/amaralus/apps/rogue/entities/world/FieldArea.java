package amaralus.apps.rogue.entities.world;

import java.util.List;

public class FieldArea {

    private final List<List<Cell>> areaCells;
    private final int height;
    private final int width;

    public FieldArea(List<List<Cell>> areaCells) {
        this.areaCells = areaCells;
        height = areaCells.size();
        width = areaCells.get(0).size();
    }

    public List<List<Cell>> getAreaCells() {
        return areaCells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
