package amaralus.apps.rogue.entities.items;

import amaralus.apps.rogue.graphics.GraphicsComponent;

import java.util.Objects;

public class Item {

    private static long itemIdGlobal = 0;

    private final long itemId;
    private String name;
    private GraphicsComponent graphicsComponent;

    public Item(String name, GraphicsComponent graphicsComponent) {
        itemId = ++itemIdGlobal;
        this.name = name;
        this.graphicsComponent = graphicsComponent;
    }

    public long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId &&
                Objects.equals(name, item.name) &&
                Objects.equals(graphicsComponent, item.graphicsComponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, graphicsComponent);
    }
}
