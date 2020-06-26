package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.entities.items.Inventory;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.services.game.EventJournal;

import static amaralus.apps.rogue.generators.RandomGenerator.randInt;
import static amaralus.apps.rogue.generators.UniqueIdGenerator.uniqueId;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

public abstract class Unit implements Destroyable, UpdatedEntity {

    private final long id = uniqueId();
    private GraphicsComponent graphicsComponent;

    private String name;
    protected Cell currentCell;

    private int healthUpLimit;
    private int health;
    private boolean alive = true;
    private boolean invulnerable = false;

    protected Inventory inventory = new Inventory();

    public Unit(GraphicsComponent graphicsComponent, String name, int health) {
        this.graphicsComponent = graphicsComponent;
        this.name = name;
        this.health = health;
        healthUpLimit = health;
    }

    @Override
    public long id() {
        return id;
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
        if (nextCell != null && nextCell.isCanWalk()) {
            if (nextCell.containsUnit()) {
                attackUnit(nextCell.getUnit());
            } else {
                currentCell.setUnit(null);
                currentCell = nextCell;
                currentCell.setUnit(this);
            }
        }
    }

    private void attackUnit(Unit targetUnit) {
        if (!targetUnit.invulnerable) {
            int damage = randInt(15, 30);
            targetUnit.removeHealthPoints(damage);
            if (this instanceof PlayerUnit || targetUnit instanceof PlayerUnit)
                getService(EventJournal.class).logEvent("%s атакует %s нанося %d едениц урона!", name, targetUnit.name, damage);
        }
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public void setGraphicsComponent(GraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }

    public String getName() {
        return name;
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
        if (health > healthUpLimit)
            health = healthUpLimit;
    }

    public void removeHealthPoints(int healthPoints) {
        health -= healthPoints;

        if (health < 1) {
            alive = false;
            health = 0;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
