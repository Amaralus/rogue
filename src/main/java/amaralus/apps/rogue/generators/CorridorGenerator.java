package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Corridor;
import amaralus.apps.rogue.entities.world.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.entities.world.CellType.CORRIDOR;
import static amaralus.apps.rogue.entities.world.CellType.EMPTY;
import static amaralus.apps.rogue.entities.world.CellType.WALL;
import static amaralus.apps.rogue.generators.RandomGenerator.*;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.CORRIDOR_FLOR;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.DOOR;

public class CorridorGenerator {

    public Corridor generateCorridor(Room startRoom, Room finishRoom) {

        List<Cell> corridorCells;
        boolean validCorridor;
        do {
            Cell from = startRoom.getRandCell();
            Cell to = finishRoom.getRandCell();

            List<Direction> directions = corridorDirections(from, to, randBoolean());

            corridorCells = new ArrayList<>();
            validCorridor = tryGenerate(from, to, directions, corridorCells);
        } while (!validCorridor);

        corridorCells.forEach(this::updateCell);

        Corridor corridor = new Corridor(corridorCells.stream()
                .filter(cell -> CORRIDOR == cell.getType() || WALL == cell.getType())
                .collect(Collectors.toList()));

        corridor.addRoom(startRoom);
        corridor.addRoom(finishRoom);
        startRoom.addCorridor(corridor);
        finishRoom.addCorridor(corridor);

        return corridor;
    }

    private boolean tryGenerate(Cell from, Cell to, List<Direction> directions, List<Cell> corridorCells) {
        Cell startCell = from;
        for (Direction direction : directions) {

            List<Cell> cells = cellsByDirection(startCell, direction, countCells(from, to, direction));
            startCell = cells.get(cells.size() - 1);

            if (!validateCells(cells, direction))
                return false;

            corridorCells.addAll(cells);
        }
        return true;
    }

    private boolean validateCells(List<Cell> cells, Direction direction) {
        // конечная клетка не может быть стеной
        if (WALL == cells.get(cells.size() - 1).getType())
            return false;

        // 3 стены подряд это плохо, 2 подряд могут быть 2 комнатами в упор
        for (Cell cell : cells)
            if (WALL == cell.getType()
                    && WALL == direction.nextCell(cell).getType()
                    && WALL == direction.nextCell(direction.nextCell(cell)).getType())
                return false;

        return true;
    }

    private List<Direction> corridorDirections(Cell from, Cell to, boolean altWay) {
        List<Direction> directions = new ArrayList<>();
        directions.add(directionByX(from, to));
        directions.add(directionByY(from, to));

        if (altWay) Collections.reverse(directions);

        directions.remove(null);

        return directions;
    }

    private Direction directionByX(Cell from, Cell to) {
        if (from.getPosition().x() == to.getPosition().x())
            return null;
        else if (from.getPosition().x() < to.getPosition().x())
            return RIGHT;
        else
            return LEFT;
    }

    private Direction directionByY(Cell from, Cell to) {
        if (from.getPosition().y() == to.getPosition().y())
            return null;
        else if (from.getPosition().y() < to.getPosition().y())
            return BOTTOM;
        else
            return TOP;
    }

    private int countCells(Cell from, Cell to, Direction direction) {
        if (direction == LEFT || direction == RIGHT)
            return countCells(from, to, Position::x);
        else
            return countCells(from, to, Position::y);
    }

    private int countCells(Cell from, Cell to, ToIntFunction<Position> axis) {
        int count = axis.applyAsInt(to.getPosition()) - axis.applyAsInt(from.getPosition());
        if (count < 0) count *= -1;
        return ++count;
    }

    private List<Cell> cellsByDirection(Cell startCell, Direction direction, int count) {
        List<Cell> cells = new ArrayList<>(count);

        Cell current = startCell;
        for (int i = 0; i < count; i++) {
            cells.add(current);
            current = direction.nextCell(current);
        }

        return cells;
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
}
