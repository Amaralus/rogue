package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.FieldArea;
import amaralus.apps.rogue.entities.world.Room;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.GameField;
import amaralus.apps.rogue.graphics.DefaultComponentsPool;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.entities.world.CellType.*;
import static amaralus.apps.rogue.generators.RandomGenerator.*;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.*;

public class WorldGenerator {

    private GameField gameField;
    private BspAreaGenerator areaGenerator = new BspAreaGenerator();

    public WorldGenerator(GameField gameField) {
        this.gameField = gameField;
    }

    public void generateDungeon() {
        List<FieldArea> areaList = areaGenerator.generateArea(gameField);

        int roomCount = randInt(4, areaList.size());
        areaList = getRandomAreas(areaList, roomCount);

        List<Room> roomList = new ArrayList<>(roomCount);
        for (FieldArea area : areaList) {
            roomList.add(generateRoomInArea(area));
        }

        for (Room room : roomList) {
            generateCorridor(randPositionFromRoom(room), randPositionFromRoom(randElement(roomList)));
        }
    }

    private Room generateRoomInArea(FieldArea area) {
        int roomHeight = randInt(5, area.getHeight());
        int roomWidth = randInt(5, area.getWidth());

        if (roomWidth > 20) roomWidth = 20;

        Position areaPosition = area.getAreaCells().get(0).get(0).getPosition();
        return generateRoom(
                areaPosition.x() + randInt(area.getWidth() - roomWidth),
                areaPosition.y() + randInt(area.getHeight() - roomHeight),
                roomWidth,
                roomHeight);
    }

    private List<FieldArea> getRandomAreas(List<FieldArea> areas, int count) {
        List<FieldArea> randomAreas = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            int index = excRandInt(areas.size());
            randomAreas.add(areas.remove(index));
        }

        return randomAreas;
    }

    public Room generateRoom(int x, int y, int width, int height) {

        List<List<Cell>> roomCells = gameField.getCellLines().subList(y, y + height).stream()
                .map(list -> list.subList(x, x + width))
                .collect(Collectors.toList());

        for (int i = 0; i < roomCells.size(); i++) {
            List<Cell> cellList = roomCells.get(i);

            for (int j = 0; j < cellList.size(); j++) {
                Cell cell = cellList.get(j);

                cell.setType(WALL);

                if (i == 0 || i == roomCells.size() - 1)
                    cell.setGraphicsComponent(HORIZONTAL_WALL);
                else if (j == 0 || j == cellList.size() - 1)
                    cell.setGraphicsComponent(VERTICAL_WALL);
                else {
                    cell.setGraphicsComponent(DefaultComponentsPool.ROOM_FLOOR);
                    cell.setType(FLOOR);
                    cell.setCanWalk(true);
                }
            }
        }

        roomCells.get(0).get(0).setGraphicsComponent(TL_CORNER);
        roomCells.get(0).get(width - 1).setGraphicsComponent(TR_CORNER);
        roomCells.get(height - 1).get(0).setGraphicsComponent(BL_CORNER);
        roomCells.get(height - 1).get(width - 1).setGraphicsComponent(BR_CORNER);

        return new Room(roomCells, Position.of(x, y));
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
