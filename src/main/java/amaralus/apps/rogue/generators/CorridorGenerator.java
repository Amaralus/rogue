package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.Area;
import amaralus.apps.rogue.entities.world.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static amaralus.apps.rogue.entities.world.CellType.*;
import static amaralus.apps.rogue.generators.RandomGenerator.randBoolean;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.CORRIDOR_FLOR;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.DOOR;

public class CorridorGenerator {

    public void generateCorridor(Area area, Position from, Position to) {
        boolean altWay = randBoolean();

        List<UnaryOperator<Cell>> movementList = getFuncList(from, to, altWay);
        movementList.addAll(getFuncList(from, to, !altWay));

        Cell currentCell = area.getCell(from);
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
