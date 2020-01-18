package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponent;

public class Unit {

    private GraphicsComponent graphicsComponent;
    protected Cell currentCell;

    public Unit(GraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }

    public void move(Direction direction) {
        Cell nextCell = direction.nextCell(currentCell);
        if (nextCell != null && nextCell.isCanWalk() && nextCell.notContainsUnit()) {
            currentCell.setUnit(null);
            currentCell = nextCell;
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
