package amaralus.apps.rogue.entities.items;

import amaralus.apps.rogue.graphics.GraphicsComponent;

import java.util.Objects;

public class Item {

    private final int itemId;
    private final String name;
    private boolean stackable;
    private int count;

    private GraphicsComponent graphicsComponent;

    public Item(int itemId, String name, GraphicsComponent graphicsComponent) {
        this(itemId, name, graphicsComponent, true, 1);
    }

    public Item(int itemId, String name, GraphicsComponent graphicsComponent, int count) {
        this(itemId, name, graphicsComponent, true, count);
    }

    public Item(int itemId, String name, GraphicsComponent graphicsComponent, boolean stackable) {
        this(itemId, name, graphicsComponent, stackable, 1);
    }

    public Item(int itemId, String name, GraphicsComponent graphicsComponent, boolean stackable, int count) {
        this.itemId = itemId;
        this.name = name;
        this.stackable = stackable;
        this.graphicsComponent = graphicsComponent;
        setCount(count);
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int count() {
        return count;
    }

    public void setCount(int count) {
        if (count < 1) throw new IllegalArgumentException();
        this.count = count;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void removeCount(int count) {
        this.count -= count;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
