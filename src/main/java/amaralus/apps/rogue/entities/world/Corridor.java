package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Entity;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.generators.UniqueIdGenerator.uniqueId;

public class Corridor implements Entity, Destroyable {

    private final long id = uniqueId();

    private List<Cell> cells;
    private List<Room> rooms;

    public Corridor(List<Cell> cells) {
        this.cells = cells;
        rooms = new ArrayList<>(2);
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public void destroy() {
        cells = null;
        rooms.clear();
    }

    public boolean contains(Cell cell) {
        return cells.contains(cell);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
