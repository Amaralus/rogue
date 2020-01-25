package amaralus.apps.rogue.entities.world;


import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.excRandInt;

public class Room extends Area {

    private List<Corridor> corridors;

    public Room(List<List<Cell>> cells) {
        super(cells);
        corridors = new ArrayList<>();
    }

    @Override
    public Cell getRandCell() {
        return getCell(excRandInt(0, getWidth() - 1), excRandInt(0, getHeight() - 1));
    }

    public List<Corridor> getCorridors() {
        return corridors;
    }

    public void setCorridors(List<Corridor> corridors) {
        this.corridors = corridors;
    }

    public void addCorridor(Corridor corridor) {
        corridors.add(corridor);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
