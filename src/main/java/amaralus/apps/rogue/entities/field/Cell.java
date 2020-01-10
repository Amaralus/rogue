package amaralus.apps.rogue.entities.field;

import amaralus.apps.rogue.entities.Unit;
import amaralus.apps.rogue.graphics.EntitySymbol;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.Palette;

public class Cell {

    private Cell topCell;
    private Cell bottomCell;
    private Cell rightCell;
    private Cell leftCell;

    private GraphicsComponent graphicsComponent;

    private Unit entity;
    private boolean canWalk = false;

    public Cell() {
        graphicsComponent = new GraphicsComponent(EntitySymbol.SPACE, Palette.BLACK_BLUE);
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

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public GraphicsComponent getActualGraphicsComponent() {
        if (containsEntity())
            return entity.getGraphicsComponent();
        else
            return graphicsComponent;
    }

    // сущность

    public Unit getEntity() {
        return entity;
    }

    public void setEntity(Unit unit) {
        this.entity = unit;
    }

    public boolean containsEntity() {
        return entity != null;
    }

    public boolean notContainsEntity() {
        return !containsEntity();
    }

    // возможность пройти

    public void setCanWalk(boolean canWalk) {
        this.canWalk = canWalk;
    }

    public boolean isCanWalk() {
        return notContainsEntity() && canWalk;
    }
}
