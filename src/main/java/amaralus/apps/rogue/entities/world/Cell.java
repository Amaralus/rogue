package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.graphics.DefaultComponentsPool;
import amaralus.apps.rogue.graphics.GraphicsComponent;

public class Cell {

    private Position position;
    private Cell topCell;
    private Cell bottomCell;
    private Cell rightCell;
    private Cell leftCell;

    private GraphicsComponent graphicsComponent;
    private CellType type;

    private Unit unit;
    private boolean canWalk = false;

    Cell(Position position) {
        this.position = position;
        graphicsComponent = DefaultComponentsPool.EMPTY_CELL;
        type = CellType.EMPTY;
    }

    public Position getPosition() {
        return position;
    }

    // тип клетки

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    // соседние клетки

    public Cell getTopCell() {
        return topCell;
    }

    public Cell getBottomCell() {
        return bottomCell;
    }

    public Cell getRightCell() {
        return rightCell;
    }

    public Cell getLeftCell() {
        return leftCell;
    }

    void setTopCell(Cell topCell) {
        this.topCell = topCell;
    }

    void setBottomCell(Cell bottomCell) {
        this.bottomCell = bottomCell;
    }

    void setRightCell(Cell rightCell) {
        this.rightCell = rightCell;
    }

    void setLeftCell(Cell leftCell) {
        this.leftCell = leftCell;
    }
    
    public boolean topCellExist() {
        return topCell != null;
    }

    public boolean topCellNotExist() {
        return !topCellExist();
    }

    public boolean bottomCellExist() {
        return bottomCell != null;
    }

    public boolean bottomCellNotExist() {
        return !bottomCellExist();
    }

    public boolean rightCellExist() {
        return rightCell != null;
    }

    public boolean rightCellNotExist() {
        return !rightCellExist();
    }

    public boolean leftCellExist() {
        return leftCell != null;
    }

    public boolean leftCellNotExist() {
        return !leftCellExist();
    }

    // графика

    public void setGraphicsComponent(GraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public GraphicsComponent getActualGraphicsComponent() {
        if (containsUnit())
            return unit.getGraphicsComponent();
        else
            return graphicsComponent;
    }

    // сущность

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean containsUnit() {
        return unit != null;
    }

    public boolean notContainsUnit() {
        return !containsUnit();
    }

    // возможность пройти

    public void setCanWalk(boolean canWalk) {
        this.canWalk = canWalk;
    }

    public boolean isCanWalk() {
        return canWalk;
    }
}
