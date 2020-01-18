package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.units.Unit;

import java.util.List;

public class Level {

    private Area gameField;
    private List<Area> areas;
    private List<Room> rooms;

    public Level(Area gameField) {
        this.gameField = gameField;
    }

    public void addUnit(Unit entity, Position position) {
        Cell cell = gameField.getCell(position);
        if (cell.isCanWalk() && cell.notContainsUnit()) {
            cell.setUnit(entity);
            entity.setCurrentCell(cell);
        }
    }

    public Area getGameField() {
        return gameField;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
