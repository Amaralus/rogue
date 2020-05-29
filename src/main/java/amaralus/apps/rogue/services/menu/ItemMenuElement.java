package amaralus.apps.rogue.services.menu;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.items.Item;

import java.util.Objects;

public class ItemMenuElement extends MenuElement {

    private Item item;

    public ItemMenuElement(Item item, Runnable runnable) {
        super(null, runnable);
        this.item = item;
        text = getItemInfo(item);
    }

    public ItemMenuElement(Item item, Command<Object> command) {
        super(null, command);
        this.item = item;
        text = getItemInfo(item);
    }

    private String getItemInfo(Item item) {
        String itemInfo = item.getGraphicsComponent().getEntitySymbol().getChar() + " " + item.getName();
        return item.count() == 1 ? itemInfo : itemInfo + " (" + item.count() + ")";
    }

    @Override
    public void executeCommandWithObject() {
        command.execute(item);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ItemMenuElement that = (ItemMenuElement) o;
        return Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), item);
    }
}
