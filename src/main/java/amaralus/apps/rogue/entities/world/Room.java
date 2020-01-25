package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.generators.RandomGenerator.excRandInt;

public class Room extends Area {

    public Room(List<List<Cell>> cells) {
        super(cells);
    }

    @Override
    public Cell getRandCell() {
        return getCell(excRandInt(0, getWidth() - 1), excRandInt(0, getHeight() - 1));
    }

    public List<Cell> wallsWithoutCorners(Direction side) {
        List<Cell> wallList;
        switch (side) {
            case TOP:
                wallList = cellsByX(0);
                break;
            case BOTTOM:
                wallList = cellsByX(getHeight() - 1);
                break;
            case RIGHT:
                wallList = cellsByY(getWidth() - 1);
                break;
            case LEFT:
                wallList = cellsByY(0);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        wallList.remove(wallList.size() - 1);
        wallList.remove(0);
        return wallList;
    }

    private List<Cell> cellsByX(int x) {
        return new ArrayList<>(getCells().get(x));
    }

    private List<Cell> cellsByY(int y) {
        return getCells().stream()
                .map(cells -> cells.get(y))
                .collect(Collectors.toList());
    }
}
