package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;
import javafx.scene.input.KeyCode;

import java.util.stream.Collectors;

import static amaralus.apps.rogue.commands.Command.NULLABLE_COM;
import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;

public class InventoryScreen extends MenuScreen {

    public InventoryScreen() {
        super("Инвентарь");
        ServiceLocator.register(this);

        setUpMenuList();

        commandPool.put(KeyCode.D, new Command<>(this::dropItem));
    }

    @Override
    protected void setUpMenuList() {
        menuList.setUpMenuList(gameScreen().getPlayer().getInventory().getItemList().stream()
                .map(item -> new MenuElement(getItemInfo(item), NULLABLE_COM))
                .collect(Collectors.toList()));
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(gameScreen()));
    }

    private String getItemInfo(Item item) {
        String itemInfo = item.getGraphicsComponent().getEntitySymbol().getChar() + " " + item.getName();
        return item.count() == 1 ? itemInfo : itemInfo + " (" + item.count() + ")";
    }

    private void dropItem() {
        if (menuList.getElementList().isEmpty()) return;

        Unit player = gameScreen().getPlayer();
        int itemIndex = menuList.getElementList().indexOf(menuList.current());

        Item item = player.getInventory().getItemList().get(itemIndex);
        player.removeItemFromInventory(item);
        player.getCurrentCell().setItem(item);

        setUpMenuList();
    }
}
