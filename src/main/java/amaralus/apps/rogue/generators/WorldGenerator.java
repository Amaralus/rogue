package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.FieldArea;
import amaralus.apps.rogue.entities.world.Room;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.GameField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static amaralus.apps.rogue.entities.world.CellType.*;
import static amaralus.apps.rogue.generators.RandomGenerator.*;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.*;

public class WorldGenerator {

    private GameField gameField;
    private RoomGenerator roomGenerator;
    private BspAreaGenerator areaGenerator;

    public WorldGenerator(GameField gameField) {
        this.gameField = gameField;
        roomGenerator = new RoomGenerator();
        areaGenerator = new BspAreaGenerator();
    }

    public Room generateDungeon() {
        List<FieldArea> areaList = areaGenerator.generateArea(gameField);

        int roomCount = randInt(4, areaList.size());
        areaList = randUniqueElements(areaList, roomCount);

        List<Room> roomList = new ArrayList<>(roomCount);
        for (FieldArea area : areaList) {
            roomList.add(roomGenerator.generateRoom(area));
        }

        for (Room room : roomList) {
            generateCorridor(randPositionFromRoom(room), randPositionFromRoom(randElement(roomList)));
        }

        return randElement(roomList);
    }

    public void generateCorridor(Position from, Position to) {
        boolean altWay = randBoolean();

        List<UnaryOperator<Cell>> movementList = getFuncList(from, to, altWay);
        movementList.addAll(getFuncList(from, to, !altWay));

        Cell currentCell = gameField.getCell(from.x(), from.y());
        updateCell(currentCell);

        for (UnaryOperator<Cell> next : movementList) {
            currentCell = next.apply(currentCell);
            updateCell(currentCell);
        }
    }

    private void updateCell(Cell cell) {
        cell.setCanWalk(true);
        if (EMPTY == cell.getType()) {
            cell.setGraphicsComponent(CORRIDOR_FLOR);
            cell.setType(CORRIDOR);
        }
        if (WALL == cell.getType())
            cell.setGraphicsComponent(DOOR);
    }

    private List<UnaryOperator<Cell>> getFuncList(Position from, Position to, boolean altWay) {
        if (altWay)
            return getFuncList(getXNextCellFunction(from, to), to.x() - from.x());
        else
            return getFuncList(getYNextCellFunction(from, to), to.y() - from.y());
    }

    private List<UnaryOperator<Cell>> getFuncList(UnaryOperator<Cell> func, int count) {
        if (count < 0)
            count *= -1;

        List<UnaryOperator<Cell>> funcList = new ArrayList<>(count);

        if (func == null) return funcList;

        for (int i = 0; i < count; i++)
            funcList.add(func);

        return funcList;
    }

    private UnaryOperator<Cell> getXNextCellFunction(Position from, Position to) {
        if (from.x() == to.x())
            return null;
        else if (from.x() < to.x())
            return Cell::getRightCell;
        else
            return Cell::getLeftCell;
    }

    private UnaryOperator<Cell> getYNextCellFunction(Position from, Position to) {
        if (from.y() == to.y())
            return null;
        else if (from.y() < to.y())
            return Cell::getBottomCell;
        else
            return Cell::getTopCell;
    }
}
