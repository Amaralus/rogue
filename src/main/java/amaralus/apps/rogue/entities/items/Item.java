package amaralus.apps.rogue.entities.items;

import amaralus.apps.rogue.graphics.GraphicsComponent;

import java.util.Objects;

public class Item {

    private final int id;
    private final String name;
    private boolean stackable;
    private int count;

    private GraphicsComponent graphicsComponent;

    public Item(int id, String name, GraphicsComponent graphicsComponent) {
        this(id, name, graphicsComponent, true, 1);
    }

    public Item(int id, String name, GraphicsComponent graphicsComponent, int count) {
        this(id, name, graphicsComponent, true, count);
    }

    public Item(int id, String name, GraphicsComponent graphicsComponent, boolean stackable) {
        this(id, name, graphicsComponent, stackable, 1);
    }

    public Item(int id, String name, GraphicsComponent graphicsComponent, boolean stackable, int count) {
        this.id = id;
        this.name = name;
        this.stackable = stackable;
        this.graphicsComponent = graphicsComponent;
        setCount(count);
    }

    public int getId() {
        return id;
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
        return Objects.equals(name, item.name) &&
                Objects.equals(graphicsComponent, item.graphicsComponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, graphicsComponent);
    }
}
