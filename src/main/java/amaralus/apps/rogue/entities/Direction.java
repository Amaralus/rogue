package amaralus.apps.rogue.entities;

import amaralus.apps.rogue.entities.world.Cell;

import java.util.function.UnaryOperator;

public enum Direction {
    TOP(Cell::getTopCell),
    BOTTOM(Cell::getBottomCell),
    RIGHT(Cell::getRightCell),
    LEFT(Cell::getLeftCell);

    private UnaryOperator<Cell> nextCellOperator;

    Direction(UnaryOperator<Cell> nextCellOperator) {
        this.nextCellOperator = nextCellOperator;
    }

    public Cell nextCell(Cell cell) {
        return nextCellOperator.apply(cell);
    }

    public Direction reverse() {
        switch (this) {
            case TOP: return BOTTOM;
            case BOTTOM: return TOP;
            case RIGHT: return LEFT;
            case LEFT: return RIGHT;
            default: throw new UnsupportedOperationException("no reverse direction for: " + this);
        }
    }
}
