package amaralus.apps.rogue.entities.world;

import java.util.ArrayList;
import java.util.List;

public class Corridor {

    private final List<Cell> cells;
    private List<Room> rooms;

    public Corridor(List<Cell> cells) {
        this.cells = cells;
        rooms = new ArrayList<>(2);
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
