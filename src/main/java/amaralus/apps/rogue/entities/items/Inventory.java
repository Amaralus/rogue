package amaralus.apps.rogue.entities.items;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    List<Item> itemList = new ArrayList<>();

    public void addItem(Item item) {
        if (itemList.contains(item) && item.isStackable())
            itemList.get(itemList.indexOf(item)).addCount(item.count());
        else
            itemList.add(item);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
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
