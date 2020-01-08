package amaralus.apps.rogue.field;

import amaralus.apps.rogue.graphics.EntitySymbol;

public class Cell {

    private Cell topCell;
    private Cell bottomCell;
    private Cell rightCell;
    private Cell leftCell;

    private EntitySymbol entitySymbol = EntitySymbol.CENTRAL_DOT;

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

    public EntitySymbol getEntitySymbol() {
        return entitySymbol;
    }

    public void setEntitySymbol(EntitySymbol entitySymbol) {
        this.entitySymbol = entitySymbol;
    }
}
