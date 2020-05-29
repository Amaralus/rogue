package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.ItemMenuElement;
import javafx.scene.input.KeyCode;

import java.util.stream.Collectors;

import static amaralus.apps.rogue.commands.Command.NULLABLE_COM;
import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;
import static javafx.scene.input.KeyCode.ENTER;

public class InventoryScreen extends MenuScreen {

    public InventoryScreen() {
        super("Инвентарь");
        ServiceLocator.register(this);

        setUpMenuList();

        commandPool.put(KeyCode.D, new Command<>(() -> {
            if (menuList.current() != null)
                menuList.current().executeCommandWithObject();
        }));
        commandPool.put(ENTER, NULLABLE_COM);
    }

    @Override
    protected void setUpMenuList() {
        menuList.setUpMenuList(gameScreen().getPlayer().getInventory().getItemList().stream()
                .map(item -> new ItemMenuElement(item, new Command<Object>(this::dropItem)))
                .collect(Collectors.toList()));
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(gameScreen()));
    }

    private void dropItem(Object item) {

        if (menuList.getElementList().isEmpty()) return;

        Unit player = gameScreen().getPlayer();

        // todo если предмет тот же - то увеличить кол-во на клетке и выкинуть из инвентаря
        if (!player.getCurrentCell().isCanPutItem() || player.getCurrentCell().containsItem()) return;

        player.removeItemFromInventory((Item) item);
        player.getCurrentCell().setItem((Item) item);

        setUpMenuList();
    }
}
