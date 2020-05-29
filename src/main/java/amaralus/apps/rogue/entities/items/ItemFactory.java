package amaralus.apps.rogue.entities.items;

import amaralus.apps.rogue.services.ServiceLocator;

public class ItemFactory {

    public ItemFactory() {
        ServiceLocator.register(this);
    }

    public Item produce(Item itemPrototype, int cont) {
        Item item = copyItem(itemPrototype);
        if (item.isStackable())
            item.setCount(cont);

        return item;
    }

    private Item copyItem(Item item) {
        return new Item(
                item.getId(),
                item.getName(),
                item.getGraphicsComponent(),
                item.isStackable());
    }
}
