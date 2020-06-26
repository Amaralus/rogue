package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.services.menu.ItemMenuElement;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.commands.Command.NULLABLE_COM;
import static amaralus.apps.rogue.services.ServiceLocator.getService;
import static amaralus.apps.rogue.services.ServiceLocator.serviceLocator;
import static javafx.scene.input.KeyCode.ENTER;

public class InventoryScreen extends MenuScreen {

    public InventoryScreen() {
        super("Инвентарь");
        serviceLocator().register(this);

        setUpMenuList();

        commandPool.put(KeyCode.D, new Command<>(() -> {
            if (menuList.current() != null)
                menuList.current().executeCommandWithObject();
        }));
        commandPool.put(ENTER, NULLABLE_COM);
    }

    @Override
    protected void setUpMenuList() {
        if (getService(GameScreen.class).getGamePlayService().getPlayer() == null)
            menuList.setUpMenuList(new ArrayList<>());
        else
            menuList.setUpMenuList(getService(GameScreen.class).getGamePlayService().getPlayer().getInventory().getItemList().stream()
                    .map(item -> new ItemMenuElement(item, new Command<Object>(this::dropItem)))
                    .collect(Collectors.toList()));
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(getService(GameScreen.class)));
    }

    private void dropItem(Object object) {
        Item item = (Item) object;

        if (menuList.getElementList().isEmpty()) return;

        Unit player = getService(GameScreen.class).getGamePlayService().getPlayer();

        if (!player.getCurrentCell().isCanPutItem()) return;
        else if (player.getCurrentCell().containsItem()) {
            Item cellItem = player.getCurrentCell().getItem();

            if (cellItem.getId() == item.getId())
                cellItem.setCount(cellItem.count() + item.count());
            else return;
        } else
            player.getCurrentCell().setItem(item);

        player.removeItemFromInventory(item);

        setUpMenuList();
    }
}
