package amaralus.apps.rogue.entities.items;

import amaralus.apps.rogue.entities.Destroyable;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements Destroyable {

    List<Item> itemList = new ArrayList<>();

    @Override
    public void destroy() {
        itemList.clear();
    }

    public void addItem(Item item) {
        if (itemList.contains(item) && item.isStackable())
            itemList.get(itemList.indexOf(item)).addCount(item.count());
        else
            itemList.add(item);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }

    public Item getItemById(int itemId) {
        for (Item item : itemList) {
            if (item.getId() == itemId)
                return item;
        }

        return null;
    }

    public boolean containsItem(int itemId) {
        for (Item item : itemList)
            if (item.getId() == itemId)
                return true;
        return false;
    }

    public int itemCount() {
        return itemList.stream()
                .mapToInt(Item::count)
                .sum();
    }

    public int uniqueItemCount() {
        return itemList.size();
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
