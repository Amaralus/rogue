package amaralus.apps.rogue.entities.items;

import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.AMULET_OF_YENDOR;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.GOLD;

public final class ItemPrototypesPool {

    public static final Item GOLD_PROTOTYPE = new Item(1, "Золото", GOLD, true);
    public static final Item AMULET_OF_YENDOR_PROTOTYPE = new Item(2, "Амулет Йендора", AMULET_OF_YENDOR, true);

    private ItemPrototypesPool() {}
}
