package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.graphics.drawers.MenuScreenDrawer;
import amaralus.apps.rogue.services.menu.MenuList;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyCode.ENTER;

public abstract class MenuScreen extends Screen {

    protected MenuList menuList = new MenuList();
    protected String menuTitle;

    public MenuScreen(String menuTitle) {
        this();
        this.menuTitle = menuTitle;
    }

    public MenuScreen() {
        screenDrawer = new MenuScreenDrawer(this);

        commandPool.put(ESCAPE, returnToPreviousScreenCommand());
        commandPool.put(UP, new Command<>(menuList::shiftToPrevious));
        commandPool.put(DOWN, new Command<>(menuList::shiftToNext));
        commandPool.put(ENTER, new Command<>(() -> menuList.current().executeCommand()));
    }

    protected abstract void setUpMenuList();

    protected abstract Command<Object> returnToPreviousScreenCommand();

    public MenuList getMenuList() {
        return menuList;
    }

    public String getMenuTitle() {
        return menuTitle;
    }
}
