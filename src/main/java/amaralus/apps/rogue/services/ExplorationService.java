package amaralus.apps.rogue.services;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.entities.world.CellType.*;
import static amaralus.apps.rogue.utils.Utils.not;

public class ExplorationService {

    private Map<Cell, Integer> cellMap;
    private int visibleRadius;

    public List<Cell> getVisibleCells(Unit unit) {
        visibleRadius = unit.getVisibleRadius();

        if (visibleRadius == 1)
            return extractCells(initialCells(unit.getCurrentCell()));

        cellMap = initialCells(unit.getCurrentCell());

        for (int i = 1; i < visibleRadius; i++) {
            int currentNumber = i;
            int nextNumber = currentNumber + 1;
            getCurrentCells(i).stream()
                    .flatMap(cell -> getNextCells(cell, currentNumber).stream())
                    .forEach(cell -> cellMap.put(cell, nextNumber));
        }

        return extractCells(cellMap);
    }

    private Map<Cell, Integer> initialCells(Cell centralCell) {
        Map<Cell, Integer> initCells = new HashMap<>();

        for (Cell cell : Arrays.asList(centralCell, centralCell.getRightCell(), centralCell.getLeftCell())) {
            initCells.put(cell, 1);
            initCells.put(cell.getTopCell(), 1);
            initCells.put(cell.getBottomCell(), 1);
        }

        initCells.put(centralCell, 0);

        return initCells.entrySet().stream()
                .filter(entry -> entry.getKey().getType() != EMPTY)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Cell> getCurrentCells(int currentNumber) {
        return cellMap.entrySet().stream()
                .filter(entry -> entry.getValue() == currentNumber)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Cell> getNextCells(Cell currentCell, int currentNumber) {
        return Stream.of(Direction.values())
                .filter(direction -> validDirection(direction, currentNumber))
                .map(direction -> direction.nextCell(currentCell))
                .filter(Objects::nonNull)
                .filter(not(cellMap::containsKey))
                .filter(nextCell -> validCell(currentCell, nextCell))
                .collect(Collectors.toList());
    }

    private boolean validDirection(Direction direction, int currentNumber) {
        // идея упростила выражение, до этого было: если текущий номер == максимальный - 1 и при этом
        // направление это верх или низ, то это направление невалидное вернуть false
        // это нужно для нормализации видимой зоны, чтобы выглядело нормально
        return currentNumber != visibleRadius - 1 || (direction != TOP && direction != BOTTOM);
    }

    // todo доделать нормальную выборку
    private boolean validCell(Cell currentCell, Cell nextCell) {
        if (nextCell.getType() == EMPTY)
            return false;

        if (currentCell.getType() == WALL && (nextCell.getType() == FLOOR || nextCell.getType() == CORRIDOR))
            return false;

        if (currentCell.getType() == DOOR) return false;
        else return true;
    }

    private List<Cell> extractCells(Map<Cell, Integer> cellMap) {
        return new ArrayList<>(cellMap.keySet());
    }
}
