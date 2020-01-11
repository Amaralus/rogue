package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Position;

import java.util.List;

public class Room {

    private final List<List<Cell>> roomCells;
    private final Position position;
    private final int width;
    private final int height;

    public Room(List<List<Cell>> roomCells, Position position) {
        this.position = position;
        this.roomCells = roomCells;
        this.width = roomCells.get(0).size();
        this.height = roomCells.size();
    }

    public Position getPosition() {
        return position;
    }

    public List<List<Cell>> getRoomCells() {
        return roomCells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
