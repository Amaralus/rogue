package amaralus.apps.rogue.entities.items;

import static amaralus.apps.rogue.services.ServiceLocator.serviceLocator;

public class ItemFactory {

    public ItemFactory() {
        serviceLocator().register(this);
    }

    public Item produce(Item itemPrototype) {
        return produce(itemPrototype, 1);
    }

    public Item produce(Item itemPrototype, int cont) {
        Item item = copyItem(itemPrototype);
        if (item.isStackable())
            item.setCount(cont);

        return item;
    }

    private Item copyItem(Item item) {
        return new Item(
                item.getItemId(),
                item.getName(),
                item.getGraphicsComponent(),
                item.isStackable());
    }
}
