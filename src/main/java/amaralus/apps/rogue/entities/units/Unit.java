package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponent;

import java.util.ArrayList;
import java.util.List;

public class Unit implements Destroyable {

    private GraphicsComponent graphicsComponent;
    protected Cell currentCell;
    private int visibleRadius = 1;

    List<Item> inventory = new ArrayList<>();

    public Unit(GraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }

    @Override
    public void destroy() {
        currentCell = null;
        graphicsComponent = null;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
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

    public int getVisibleRadius() {
        return visibleRadius;
    }

    public void setVisibleRadius(int visibleRadius) {
        this.visibleRadius = visibleRadius;
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
