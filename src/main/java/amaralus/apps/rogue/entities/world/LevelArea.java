package amaralus.apps.rogue.entities.world;

import java.util.ArrayList;
import java.util.List;

public class LevelArea extends Area {

    private List<LevelArea> neighborAreas;
    private Room room;

    public LevelArea(List<List<Cell>> cells) {
        super(cells);
        neighborAreas = new ArrayList<>();
    }

    @Override
    public void destroy() {
        neighborAreas.clear();
        room = null;
    }

    public List<LevelArea> getNeighborAreas() {
        return neighborAreas;
    }

    public void setNeighborAreas(List<LevelArea> neighborAreas) {
        this.neighborAreas = neighborAreas;
    }

    public boolean containsRoom() {
        return room != null;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
