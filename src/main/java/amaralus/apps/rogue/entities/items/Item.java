package amaralus.apps.rogue.entities.items;

import amaralus.apps.rogue.graphics.GraphicsComponent;

import java.util.Objects;

public class Item {

    private String name;
    private boolean stackable;
    private int count;

    private GraphicsComponent graphicsComponent;

    public Item(String name, GraphicsComponent graphicsComponent) {
        this(name, graphicsComponent, true, 1);
    }

    public Item(String name, GraphicsComponent graphicsComponent, int count) {
        this(name, graphicsComponent, true, count);
    }

    public Item(String name, GraphicsComponent graphicsComponent, boolean stackable) {
        this(name, graphicsComponent, stackable, 1);
    }

    public Item(String name, GraphicsComponent graphicsComponent, boolean stackable, int count) {
        this.name = name;
        this.stackable = stackable;
        this.graphicsComponent = graphicsComponent;
        setCount(count);
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
