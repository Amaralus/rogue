package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponent;

public class Unit {

    private GraphicsComponent graphicsComponent;
    protected Cell currentCell;

    public Unit(GraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }

    public void moveTop() {
        if (currentCell.topCellExist() && currentCell.getTopCell().isCanWalk() && currentCell.getTopCell().notContainsUnit()) {
            currentCell.setUnit(null);
            currentCell = currentCell.getTopCell();
            currentCell.setUnit(this);
        }
    }

    public void moveBottom() {
        if (currentCell.bottomCellExist() && currentCell.getBottomCell().isCanWalk() && currentCell.getBottomCell().notContainsUnit()) {
            currentCell.setUnit(null);
            currentCell = currentCell.getBottomCell();
            currentCell.setUnit(this);
        }
    }

    public void moveRight() {
        if (currentCell.rightCellExist() && currentCell.getRightCell().isCanWalk() && currentCell.getRightCell().notContainsUnit()) {
            currentCell.setUnit(null);
            currentCell = currentCell.getRightCell();
            currentCell.setUnit(this);
        }
    }

    public void moveLeft() {
        if (currentCell.leftCellExist() && currentCell.getLeftCell().isCanWalk() && currentCell.getLeftCell().notContainsUnit()) {
            currentCell.setUnit(null);
            currentCell = currentCell.getLeftCell();
            currentCell.setUnit(this);
        }
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public void setGraphicsComponent(GraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public Position getPosition() {
        return currentCell.getPosition();
    }
}
