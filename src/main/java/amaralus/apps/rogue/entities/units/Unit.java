package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.entities.items.Inventory;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponent;

public abstract class Unit implements Destroyable, UpdatedEntity {

    private GraphicsComponent graphicsComponent;

    protected Cell currentCell;
    private int health;
    private boolean immortal = false;

    protected Inventory inventory = new Inventory();

    public Unit(GraphicsComponent graphicsComponent, int health) {
        this.graphicsComponent = graphicsComponent;
        this.health = health;
    }

    @Override
    public void destroy() {
        currentCell = null;
        graphicsComponent = null;
        inventory.destroy();
    }

    public void addItemToInventory(Item item) {
        inventory.addItem(item);
    }

    public void removeItemFromInventory(Item item) {
        inventory.removeItem(item);
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void addHealthPoints(int healthPoints) {
        health += healthPoints;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
