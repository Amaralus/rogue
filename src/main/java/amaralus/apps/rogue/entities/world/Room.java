package amaralus.apps.rogue.entities.world;

import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.excRandInt;

public class Room extends Area {

    public Room(List<List<Cell>> cells) {
        super(cells);
    }

    @Override
    public Cell getRandCell() {
        return getCell(excRandInt(0, getWidth() - 1), excRandInt(0, getHeight() - 1));
    }
}
