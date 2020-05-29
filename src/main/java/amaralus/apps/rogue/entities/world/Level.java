package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;

import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.randElement;

public class Level implements Destroyable {

    private Area gameField;
    private List<Area> areas;
    private List<Room> rooms;

    public Level(Area gameField) {
        this.gameField = gameField;
    }

    @Override
    public void destroy() {
        gameField.destroy();
    }

    public void setUpUnit(Unit unit, Position position) {
        Cell cell = gameField.getCell(position);
        if (cell.isCanWalk() && cell.notContainsUnit()) {
            cell.setUnit(unit);
            unit.setCurrentCell(cell);
        }
    }

    public void setUpUnitToRandRoom(Unit unit) {
        setUpUnit(unit, randElement(rooms).getRandCellPosition());
    }

    public void setUpItem(Item item, Position position) {
        Cell cell = gameField.getCell(position);
        if (cell.isCanPutItem() && cell.notContainsItem())
            cell.setItem(item);
    }

    public void setUpItemToRandRoom(Item item) {
        setUpItem(item, randElement(rooms).getRandCellPosition());
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
