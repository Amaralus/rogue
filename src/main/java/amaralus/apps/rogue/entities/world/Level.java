package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;

import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.randElement;

public class Level implements Destroyable {

    private Area gameField;
    private List<LevelArea> levelAreas;
    private List<Room> rooms;
    private List<Corridor> corridors;

    public Level(Area gameField) {
        this.gameField = gameField;
    }

    @Override
    public void destroy() {
        gameField.destroy();
        levelAreas.forEach(Destroyable::destroy);
        levelAreas.clear();
        rooms.forEach(Destroyable::destroy);
        rooms.clear();
        corridors.forEach(Destroyable::destroy);
    }

    public boolean setUpUnit(Unit unit, Position position) {
        Cell cell = gameField.getCell(position);
        if (cell.isCanWalk() && cell.notContainsUnit()) {
            cell.setUnit(unit);
            unit.setCurrentCell(cell);
            return true;
        } else
            return false;
    }

    public boolean setUpUnitToRandRoom(Unit unit) {
        return setUpUnit(unit, randElement(rooms).getRandCellPosition());
    }

    public boolean setUpItem(Item item, Position position) {
        Cell cell = gameField.getCell(position);
        if (cell.isCanPutItem() && cell.notContainsItem()) {
            cell.setItem(item);
            return true;
        } else
            return false;
    }

    public Room getRoomByCell(Cell cell) {
        for (Room room : rooms) {
            if (room.contains(cell))
                return room;
        }
        return null;
    }

    public Corridor getCorridorByCell(Cell cell) {
        for (Corridor corridor : corridors) {
            if (corridor.contains(cell))
                return corridor;
        }
        return null;
    }


    public boolean setUpItemToRandRoom(Item item) {
        return setUpItem(item, randElement(rooms).getRandCellPosition());
    }

    public Area getGameField() {
        return gameField;
    }

    public List<LevelArea> getLevelAreas() {
        return levelAreas;
    }

    public void setLevelAreas(List<LevelArea> levelAreas) {
        this.levelAreas = levelAreas;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Corridor> getCorridors() {
        return corridors;
    }

    public void setCorridors(List<Corridor> corridors) {
        this.corridors = corridors;
    }
}
