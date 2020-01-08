package amaralus.apps.rogue.field;

import amaralus.apps.rogue.graphics.EntitySymbol;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.Palette;

public class Cell {

    private Cell topCell;
    private Cell bottomCell;
    private Cell rightCell;
    private Cell leftCell;

    private GraphicsComponent graphicsComponent;

    public Cell() {
        graphicsComponent = new GraphicsComponent(EntitySymbol.SPACE, Palette.BLACK_BLUE);
    }

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

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }
}
