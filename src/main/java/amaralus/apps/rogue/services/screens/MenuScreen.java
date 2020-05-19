package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.graphics.drawers.MenuScreenDrawer;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;
import amaralus.apps.rogue.services.menu.MenuList;

import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;
import static javafx.scene.input.KeyCode.*;


public class MenuScreen extends Screen {

    protected MenuList menuList = new MenuList();
    private String menuTitle = "Меню, используй [\u2191] и [\u2193] для смещения, [Enter] для выбора";

    public MenuScreen() {
        screenDrawer = new MenuScreenDrawer(this);

        createMenuList();

        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(gameScreen())));
        commandPool.put(UP, new Command<>(menuList::shiftToPrevious));
        commandPool.put(DOWN, new Command<>(menuList::shiftToNext));
        commandPool.put(ENTER, new Command<>(() -> menuList.current().executeComand()));
    }

    protected void createMenuList() {
        menuList.setUpMenuList(
                new MenuElement("Продолжить", () -> setActiveScreen(gameScreen())),
                new MenuElement("Выйти из игры", () -> ServiceLocator.gameController().exitGame())
        );
    }

    @Override
    public void update() {
        inputCommand.execute();
        inputCommand = Command.NULLABLE_COM;
    }

    public MenuList getMenuList() {
        return menuList;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }
}
