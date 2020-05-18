package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.services.menu.MenuElement;

import java.util.stream.Collectors;

import static amaralus.apps.rogue.commands.Command.NULLABLE_COM;

public class InventoryScreen extends MenuScreen {

    public InventoryScreen(GameScreen gameScreen) {
        super(gameScreen);
        setMenuTitle("Инвентарь");
    }

    @Override
    protected void createMenuList() {
        menuList.setUpMenuList(gameScreen.getPlayer().getInventory().stream()
                .map(item -> new MenuElement(item.getName(), NULLABLE_COM))
                .collect(Collectors.toList()));
    }
}
